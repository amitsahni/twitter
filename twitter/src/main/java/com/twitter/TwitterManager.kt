package com.twitter

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterAuthClient

object TwitterManager {

    @JvmStatic
    internal val auth = FirebaseAuth.getInstance()

    @JvmStatic
    val twitterAuthClient: TwitterAuthClient = TwitterAuthClient()

    @JvmStatic
    val session: TwitterSession?
        get() {
            return TwitterCore.getInstance()
                .sessionManager.activeSession
        }

    @JvmStatic
    val user: UserInfo?
        get() {

            FirebaseAuth.getInstance().currentUser?.let {
                it.providerData.forEach {
                    if (it.providerId == "twitter.com") {
                        return it
                    }
                }
            }
            return null
        }

    @JvmStatic
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        twitterAuthClient.onActivityResult(requestCode, resultCode, data)
    }
}
