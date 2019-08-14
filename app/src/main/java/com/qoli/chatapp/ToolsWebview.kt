package com.qoli.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.tools_webview.*

class ToolsWebview : AppCompatActivity() {

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
        setContentView(R.layout.tools_webview)

        val url = intent.getStringExtra("url")

        supportActionBar?.title = "查看网页"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (url != "") {
            webview.loadUrl("$url")
        } else {
            webview.loadUrl("https://www.baidu.com")
        }
    }

}
