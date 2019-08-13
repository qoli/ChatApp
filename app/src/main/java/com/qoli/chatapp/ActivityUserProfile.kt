package com.qoli.chatapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_user_profile.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick

class ActivityUserProfile : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val usernameString = intent.getStringExtra("username").toString()
        username.text = usernameString

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.title = usernameString



        chatButton.onClick {
            startActivity(intentFor<ActivityChat>("username" to "$usernameString").addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }
    }
}