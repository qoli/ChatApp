package com.qoli.chatapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qoli.chatapp.Welcome.WelcomeSign
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.app_video_background.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.io.IOError


class ActivityWelcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // hide ActionBar
        supportActionBar?.hide()

        try {
            // video
            videoView.setRawData(R.raw.appbg)
            videoView.isLooping = true
            videoView.prepare { mp ->
                mp.start()
            }
        } catch (error:IOError) {
            print(error)
        }

        loginButton.onClick {
            startActivity<WelcomeSign>(("action" to "signin"))
        }

        registerButton.onClick {
            startActivity<WelcomeSign>(("action" to "signup"))
        }

        social_qq.onClick {
            toast("qq")
        }

        social_weibo.onClick {
            toast("weibo")
        }

        social_weixin.onClick {
            toast("weixin")
        }

    }

}
