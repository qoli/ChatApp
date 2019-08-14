package com.qoli.chatapp.Welcome

import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.qoli.chatapp.AppFunction.FormUtil
import com.qoli.chatapp.CompoundView.inputGroupUI
import com.qoli.chatapp.R
import com.qoli.chatapp.Setting.SettingGender
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.textChangedListener


class WelcomePassword : AppCompatActivity(), AnkoLogger {

    var passwordString: String = ""
    var authString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val ui = WelcomePasswordUI()
        ui.setContentView(this)

        var formTest = FormUtil()

        ui.password.editText.textChangedListener {
            afterTextChanged { text ->
                passwordString = text.toString()
                ui.password.helperText.text = ""
            }
        }

        ui.auth.editText.textChangedListener {
            afterTextChanged { text ->
                authString = text.toString()
                info { "$passwordString $authString" }
                ui.auth.helperText.text = ""
                if (authString == passwordString) {
                    ui.next.isEnabled = true
                    ui.next.textColor = ContextCompat.getColor(applicationContext, R.color.white)
                } else {
                    ui.next.isEnabled = false
                    ui.next.textColor = ContextCompat.getColor(applicationContext, R.color.whitea50)
                }
            }
        }

        ui.next.onClick {
            if (!formTest.isPassword(passwordString)) {
                ui.password.helperText.text = "密码格式不符合"
            } else {
                startActivity<SettingGender>()
            }
        }


    }
}

class WelcomePasswordUI : AnkoComponent<WelcomePassword> {

    lateinit var textView: TextView
    lateinit var password: inputGroupUI
    lateinit var auth: inputGroupUI
    lateinit var next: Button

    override fun createView(ui: AnkoContext<WelcomePassword>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent)
            padding = dip(16)
            linearLayout {
                lparams(width = matchParent) {
                    bottomMargin = dip(8)
                }

                textView = themedTextView(theme = R.style.BarsTitle1Active20Black) {
                    text = "输入密码"
                }
            }

            password = inputGroupUI {
                this.textView.text = "密码"
                this.editText.hint = "6 位以上英文和数字"
                this.editText.inputType = InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            auth = inputGroupUI {
                this.textView.text = "验证"
                this.editText.hint = "再一次输入密码"
                this.editText.inputType = InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            linearLayout {
                lparams(width = matchParent) {
                    verticalMargin = dip(8)
                }

                next = themedButton("开始", R.style.ButtonMain) {
                    backgroundResource = R.drawable.button_main_bg
                    isEnabled = false
                    textColor = ContextCompat.getColor(ctx, R.color.whitea50)
                }.lparams(width = wrapContent, height = wrapContent) {
                    marginEnd = dip(16)
                }

            }
        }
    }

}