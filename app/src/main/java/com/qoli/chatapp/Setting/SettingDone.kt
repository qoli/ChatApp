package com.qoli.chatapp.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.hawk.Hawk
import com.qoli.chatapp.ActivityMain
import com.qoli.chatapp.AppString.StorageKeyName
import com.qoli.chatapp.R
import kotlinx.android.synthetic.main.setting_done.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class SettingDone : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_done)
        supportActionBar?.title = "完成"

        DoneButton.onClick {
            Hawk.put(StorageKeyName().ifLogin(), true)
            startActivity<ActivityMain>()
        }
    }
}
