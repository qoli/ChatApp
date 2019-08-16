package com.qoli.chatapp.Setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qoli.chatapp.R
import kotlinx.android.synthetic.main.setting_gender.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textColorResource

class SettingGender : AppCompatActivity() {

    private var genderString: String = "Boy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_gender)
        supportActionBar?.title = "设置性别"

        BoyFrame.onClick {
            SelectBoy.setImageResource(R.drawable.select_boy_active)
            SelectGirl.setImageResource(R.drawable.selec_oval_normal)
            SelectText.text = "男生"
            next.isEnabled = true
            next.textColorResource = R.color.white
            genderString = "Boy"
        }

        GirlFrame.onClick {
            SelectBoy.setImageResource(R.drawable.selec_oval_normal)
            SelectGirl.setImageResource(R.drawable.select_girl_active)
            SelectText.text = "女生"
            next.isEnabled = true
            next.textColorResource = R.color.white
            genderString = "Girl"
        }

        next.onClick {
            startActivity<SettingMember>()
        }
    }
}
