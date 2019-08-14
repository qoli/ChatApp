package com.qoli.chatapp.Welcome

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.qoli.chatapp.CompoundView.inputGroupUI
import com.qoli.chatapp.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class WelcomeSMSAuth : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val ui = WelcomeSMSAuthUI()
        ui.setContentView(this)

        ui.next.onClick {
            startActivity(intentFor<WelcomePassword>().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }

        ui.reSend.onClick {
            toast("RESEND")
        }

    }
}

class WelcomeSMSAuthUI : AnkoComponent<WelcomeSMSAuth> {

    lateinit var textView: TextView
    lateinit var auth: inputGroupUI
    lateinit var reSend: Button
    lateinit var next: Button

    override fun createView(ui: AnkoContext<WelcomeSMSAuth>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent)
            padding = dip(16)
            linearLayout {
                lparams(width = matchParent) {
                    bottomMargin = dip(8)
                }

                textView = themedTextView(theme = R.style.BarsTitle1Active20Black) {
                    text = "输入验证码"
                }
            }

            auth = inputGroupUI {
                this.textView.text = "验证码"
                this.editText.hint = "4 位短信验证码"
            }

            linearLayout {
                lparams(width = matchParent) {
                    verticalMargin = dip(8)
                }

                next = themedButton(R.string.nextText, R.style.ButtonMain) {
                    backgroundResource = R.drawable.button_main_bg
                }.lparams(width = wrapContent, height = wrapContent) {
                    marginEnd = dip(16)
                    stateListAnimator = null
                }

                reSend = themedButton(R.string.resend, R.style.ButtonGhost) {
                    backgroundResource = R.drawable.button_ghost_bg
                }.lparams(width = wrapContent, height = wrapContent) {
                    stateListAnimator = null
                    gravity = Gravity.END
                }
            }
        }
    }

}