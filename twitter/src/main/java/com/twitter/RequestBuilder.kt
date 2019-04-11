package com.twitter

import android.app.Activity
import android.content.Context
import com.twitter.callback.OnError
import com.twitter.callback.OnSuccess
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.auth.UserInfo
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession

class RequestBuilder {

    class LoginBuilder(private val activity: Activity) {
        private var successCallback: OnSuccess<TwitterSession>? = null
        private var errorCallback: OnError<Exception>? = null

        fun success(success: TwitterSession.() -> Unit): LoginBuilder {
            successCallback = object : OnSuccess<TwitterSession> {
                override fun onSuccess(result: TwitterSession) {
                    success(result)
                }
            }
            return this
        }

        fun error(e: Exception.() -> Unit): LoginBuilder {
            errorCallback = object : OnError<Exception> {
                override fun onError(error: Exception) {
                    e(error)
                }
            }
            return this
        }

        fun build() {
            TwitterConnect.twitterAuthClient.authorize(activity, object : Callback<TwitterSession>() {

                override fun success(twitterSessionResult: Result<TwitterSession>) {
                    successCallback?.onSuccess(twitterSessionResult.data)
                }

                override fun failure(e: TwitterException) {
                    errorCallback?.onError(e)
                }
            })
        }
    }

    class EmailBuilder {
        private var successCallback: OnSuccess<String>? = null
        private var errorCallback: OnError<TwitterException>? = null

        fun success(success: String.() -> Unit): EmailBuilder {
            successCallback = object : OnSuccess<String> {
                override fun onSuccess(result: String) {
                    success(result)
                }
            }
            return this
        }

        fun error(e: TwitterException.() -> Unit): EmailBuilder {
            errorCallback = object : OnError<TwitterException> {
                override fun onError(error: TwitterException) {
                    e(error)
                }
            }
            return this
        }

        fun build() {
            TwitterConnect.twitterAuthClient.requestEmail(TwitterConnect.session, object : Callback<String>() {
                override fun success(result: Result<String>) {
                    // Do something with the result, which provides the email address
                    result.data?.let {
                        successCallback?.onSuccess(it)
                    }
                }

                override fun failure(exception: TwitterException) {
                    // Do something on failure
                    errorCallback?.onError(exception)
                }
            })
        }

    }


    class ProfileBuilder(val context: Context) {
        private var successCallback: OnSuccess<UserInfo>? = null
        private var errorCallback: OnError<Exception>? = null

        fun success(success: UserInfo.() -> Unit): ProfileBuilder {
            successCallback = object : OnSuccess<UserInfo> {
                override fun onSuccess(result: UserInfo) {
                    success(result)
                }
            }
            return this
        }

        fun error(e: Exception.() -> Unit): ProfileBuilder {
            errorCallback = object : OnError<Exception> {
                override fun onError(error: Exception) {
                    e(error)
                }
            }
            return this
        }

        fun build() {
            TwitterConnect.session?.let {
                val credential = TwitterAuthProvider.getCredential(
                        it.authToken.token,
                        it.authToken.secret)
                TwitterConnect.auth.signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                TwitterConnect.auth.currentUser?.let {
                                    it.providerData.forEach { user ->
                                        if (user.providerId.equals("twitter.com")) {
                                            successCallback?.onSuccess(user)
                                        }
                                    }
                                }
                            } else {
                                it.exception?.let {
                                    errorCallback?.onError(it)
                                }
                            }
                        }.addOnFailureListener {
                            errorCallback?.onError(it)
                        }
            }


        }
    }

}