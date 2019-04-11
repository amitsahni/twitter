package org.twitter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.twitter.TwitterConfiguration
import com.twitter.TwitterConnect
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    internal var textView: TextView? = null
    private val TWITTER_KEY = "mpUVvDLh4EE0376IdQZfGI5vf"
    private val TWITTER_SECRET = "L8SEa7dfP1qdCSUTfrkXrI0CjY4uqNUin7tfVc3gn588CUQomm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TwitterConfiguration.keys(TWITTER_KEY, TWITTER_SECRET)
            .isDebug(BuildConfig.DEBUG)
            .config(applicationContext)

        googleTw.setOnClickListener {
            val user = TwitterConnect.user
            if (user == null) {
                TwitterConnect.with()
                    .login(this)
                    .success {
                        TwitterConnect.with()
                            .profile(this@MainActivity)
                            .success {
                                Log.i(
                                    localClassName,
                                    displayName + " " + email + "" + phoneNumber
                                )
                                Unit
                            }.build()
                    }
                    .error {
                    }.build()
            } else {
                Log.i(localClassName + "Facebook", user.displayName + " " + user.email + "" + user.phoneNumber)
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        TwitterConnect.onActivityResult(requestCode, resultCode, data!!)

    }


}