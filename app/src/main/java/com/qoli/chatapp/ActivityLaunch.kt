package com.qoli.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.startActivity

class ActivityLaunch : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_)

        // 未註冊用戶
//        startActivity<ActivityWelcome>()

        // 已註冊用戶
        startActivity<ActivityMain>()

    }
}
