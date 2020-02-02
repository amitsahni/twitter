package org.twitter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.twitter.*
//import com.twitter.onTwitterActivityResult
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    internal var textView: TextView? = null
    private val TWITTER_KEY = "mpUVvDLh4EE0376IdQZfGI5vf"
    private val TWITTER_SECRET = "L8SEa7dfP1qdCSUTfrkXrI0CjY4uqNUin7tfVc3gn588CUQomm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        googleTw.setOnClickListener {
            val user = twitterUser
            if (user == null) {
                twitterLogin({
                    twitterProfile { user, exception ->
                        Log.d(localClassName + "Twitter", user?.displayName + " " + user?.email + "" + user?.phoneNumber)
                        exception?.printStackTrace()
                        twitterToken {
                            Log.d("twitterToken", it)
                        }
                    }
                }, {
                    it.printStackTrace()
                })
            } else {
                Log.i(localClassName + "Twitter", user.displayName + " " + user.email + "" + user.phoneNumber)
                twitterToken {
                    Log.d("twitterToken", it)
                }
                twitterLogout()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onTwitterActivityResult(requestCode, resultCode, data)
    }


}