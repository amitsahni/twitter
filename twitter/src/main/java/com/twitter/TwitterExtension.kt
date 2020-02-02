package com.twitter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.auth.UserInfo
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterAuthClient

val twitterAuth = FirebaseAuth.getInstance()
val twitterAuthClient by lazy {
    TwitterAuthClient()
}

val twitterSession: TwitterSession?
    get() {
        return TwitterCore.getInstance()
                .sessionManager.activeSession
    }

val twitterUser: UserInfo?
    get() {

        twitterAuth.currentUser?.let {
            it.providerData.forEach {
                if (it.providerId == "twitter.com") {
                    return it
                }
            }
        }
        return null
    }


fun Activity.twitterLogin(success: (TwitterSession?) -> Unit, error: (Exception) -> Unit) {
    //val provider = OAuthProvider.newBuilder("twitter.com")
    twitterAuthClient.authorize(this, object : Callback<TwitterSession>() {
        override fun success(result: Result<TwitterSession>?) {
            result?.data?.apply {
                success(this)
            }
        }

        override fun failure(e: TwitterException) {
            error(e)
        }
    })
    /*if (twitterAuth.pendingAuthResult != null) {
        twitterAuth.pendingAuthResult
                ?.addOnSuccessListener {
                    success(twitterUser)
                }?.addOnFailureListener {
                    error(it)
                }?.addOnCanceledListener {
                    Log.e("addOnCanceledListener", "addOnCanceledListener")
                }?.addOnCompleteListener {
                    Log.e("addOnCompleteListener", "addOnCompleteListener")
                }
    } else {
        twitterAuth.startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener {
                    success(twitterUser)
                }
                .addOnFailureListener {
                    error(it)
                }
                .addOnCanceledListener {
                    Log.e("addOnCanceledListener", "addOnCanceledListener")
                }
                .addOnCompleteListener {
                    Log.e("addOnCompleteListener", "addOnCompleteListener")
                }
    }*/
}

fun twitterToken(f: (String?) -> Unit) {
    twitterAuth.currentUser?.getIdToken(true)
            ?.addOnSuccessListener {
                f(it.token)
            }
}

fun onTwitterActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    twitterAuthClient.onActivityResult(requestCode, resultCode, data)
}

fun twitterEmail(success: (String) -> Unit, error: (TwitterException) -> Unit) {
    twitterAuthClient.requestEmail(twitterSession, object : Callback<String>() {
        override fun success(result: Result<String>) {
            result.data?.apply(success)
        }

        override fun failure(exception: TwitterException) {
            error(exception)
        }
    })
}

fun twitterProfile(f: (UserInfo?, Exception?) -> Unit) {
    twitterSession?.apply {
        val credential = TwitterAuthProvider.getCredential(
                authToken.token,
                authToken.secret
        )
        twitterAuth.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        twitterAuth.currentUser?.let {
                            it.providerData.forEach { user ->
                                if (user.providerId.equals("twitter.com")) {
                                    f(user, null)
                                }
                            }
                        }
                    } else {
                        f(null, it.exception)
                    }
                }.addOnFailureListener {
                    f(null, it)
                }
    }
}

fun twitterLogout() {
    twitterAuth.signOut()
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
