package com.qoli.chatapp.Welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.orhanobut.hawk.Hawk
import com.qoli.chatapp.ActivityChat
import com.qoli.chatapp.ActivityMain
import com.qoli.chatapp.AppString.StoreKeyName
import com.qoli.chatapp.CompoundView.inputGroupUI
import com.qoli.chatapp.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class WelcomeSign : AppCompatActivity() {

    private val ui: WelcomeSignUI = WelcomeSignUI(this)
    private var isLogin: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val action = intent.getStringExtra("action").toString()

        ui.setContentView(this)

        if (action == "signin") {
            inAction()

        } else {
            upAction()

        }

        ui.inTextView.onClick {
            inAction()
        }

        ui.upTextView.onClick {
            upAction()
        }

        ui.login.onClick {
            if (isLogin) {
                Hawk.put(StoreKeyName().ifLogin(), true)
                startActivity<ActivityMain>()
            } else {
                startActivity(intentFor<WelcomeSMSAuth>("phone" to ui.phone.editText.text).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
            }
        }

        ui.forget.onClick {
            toast("forget")
        }

    }

    private fun inAction() {
        isLogin = true
        ui.inTextView.textAppearance = R.style.BarsTitle1Active20Black
        ui.upTextView.textAppearance = R.style.BarsTitle2Normal65Black
        ui.password.isVisible = true
        ui.forget.isVisible = true
        ui.login.text = getString(R.string.loginText)
    }

    private fun upAction() {
        isLogin = false
        ui.inTextView.textAppearance = R.style.BarsTitle2Normal65Black
        ui.upTextView.textAppearance = R.style.BarsTitle1Active20Black
        ui.password.isVisible = false
        ui.forget.isVisible = false
        ui.login.text = getString(R.string.nextText)
    }
}

class WelcomeSignUI(context: Context) : AnkoComponent<WelcomeSign> {

    lateinit var inTextView: TextView
    lateinit var upTextView: TextView
    lateinit var phone: inputGroupUI
    lateinit var password: inputGroupUI
    lateinit var login: Button
    lateinit var forget: Button

    override fun createView(ui: AnkoContext<WelcomeSign>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent)
            padding = dip(16)
            linearLayout {
                lparams(width = matchParent) {
                    bottomMargin = dip(8)
                }
                inTextView = themedTextView(theme = R.style.BarsTitle2Normal65Black) {
                    text = "登录"
                }.lparams {
                    marginEnd = dip(16)
                }
                upTextView = themedTextView(theme = R.style.BarsTitle2Normal65Black) {
                    text = "注册新用户"
                }
            }

            phone = inputGroupUI {
                this.textView.text = "手机号码"
                this.editText.hint = "请输入手机号码"
                this.editText.inputType = InputType.TYPE_CLASS_PHONE
            }
            password = inputGroupUI {
                this.textView.text = "密码"
                this.editText.hint = "请输入密码"
                this.editText.inputType = InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            linearLayout {
                lparams(width = matchParent, height = matchParent) {
                    verticalMargin = dip(8)
                }
                login = themedButton("登录", R.style.ButtonMain) {
                    backgroundResource = R.drawable.button_main_bg
                }.lparams(width = wrapContent, height = wrapContent) {
                    marginEnd = dip(16)
                }
                forget = themedButton("忘记密码", theme = R.style.ButtonGhost) {
                    backgroundResource = R.drawable.button_ghost_bg
                }
            }
        }
    }

}