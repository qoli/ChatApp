package com.qoli.chatapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class ActivityLaunch : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_)

        // 未註冊用戶
        //startActivity<ActivityWelcome>()

        // 已註冊用戶
        //startActivity<ActivityMain>()

    }
}
