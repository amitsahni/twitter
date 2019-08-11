package com.twitter

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.auth.UserInfo
import com.twitter.sdk.android.core.*

fun login(activity: Activity, success: (TwitterSession) -> Unit, error: (TwitterException) -> Unit) {
    TwitterManager.twitterAuthClient.authorize(activity, object : Callback<TwitterSession>() {
        override fun success(result: Result<TwitterSession>?) {
            result?.data?.apply {
                success(this)
            }
        }

        override fun failure(e: TwitterException) {
            error(e)
        }
    })
}

fun email(success: (String) -> Unit, error: (TwitterException) -> Unit) {
    TwitterManager.twitterAuthClient.requestEmail(TwitterManager.session, object : Callback<String>() {
        override fun success(result: Result<String>) {
            result.data?.apply(success)
        }

        override fun failure(exception: TwitterException) {
            error(exception)
        }
    })
}

fun profile(success: (UserInfo) -> Unit, e: (Exception) -> Unit) {
    TwitterManager.session?.apply {
        val credential = TwitterAuthProvider.getCredential(
            authToken.token,
            authToken.secret
        )
        TwitterManager.auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    TwitterManager.auth.currentUser?.let {
                        it.providerData.forEach { user ->
                            if (user.providerId.equals("twitter.com")) {
                                success(user)
                            }
                        }
                    }
                } else {
                    it.exception?.let {
                        e(it)
                    }
                }
            }.addOnFailureListener {
                e(it)
            }
    }
}

fun Context.config(
    isDebug: Boolean = true,
    consumerKey: String, secretKey: String
) {
    val builder = TwitterConfig.Builder(this)
    builder.debug(isDebug)
    builder.logger(DefaultLogger(Log.DEBUG))
    builder.twitterAuthConfig(TwitterAuthConfig(consumerKey, secretKey))
    Twitter.initialize(builder.build())
}