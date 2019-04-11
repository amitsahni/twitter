package com.twitter

import android.content.Context
import android.util.Log

import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig


/**
 * Created by clickapps on 18/9/17.
 */

object TwitterConfiguration {
    private var consumerKey: String = ""
    private var secretKey: String = ""
    private var isDebug: Boolean = true

    @JvmStatic
    fun keys(consumerKey: String, secretKey: String): TwitterConfiguration {
        TwitterConfiguration.consumerKey = consumerKey
        TwitterConfiguration.secretKey = secretKey
        return this
    }

    fun isDebug(isDebug: Boolean): TwitterConfiguration {
        TwitterConfiguration.isDebug = isDebug
        return this
    }

    fun config(context: Context) {
        val builder = TwitterConfig.Builder(context)
        builder.debug(isDebug)
        builder.logger(DefaultLogger(Log.DEBUG))
        builder.twitterAuthConfig(TwitterAuthConfig(consumerKey, secretKey))
        Twitter.initialize(builder.build())
    }

}
