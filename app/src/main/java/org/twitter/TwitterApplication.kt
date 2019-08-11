package org.twitter

import androidx.multidex.MultiDexApplication
import com.twitter.config


class TwitterApplication : MultiDexApplication() {
    private val TWITTER_KEY = "mpUVvDLh4EE0376IdQZfGI5vf"
    private val TWITTER_SECRET = "L8SEa7dfP1qdCSUTfrkXrI0CjY4uqNUin7tfVc3gn588CUQomm"
    override fun onCreate() {
        super.onCreate()
        config(true, TWITTER_KEY, TWITTER_SECRET)
    }
}