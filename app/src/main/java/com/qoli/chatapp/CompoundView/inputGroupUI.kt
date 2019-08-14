package com.qoli.chatapp.CompoundView

import android.content.Context
import android.graphics.Typeface
import android.text.InputType
import android.view.View
import android.view.ViewManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.qoli.chatapp.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.inputGroupUI(theme: Int = 0) = inputGroupUI(theme) {}
inline fun ViewManager.inputGroupUI(theme: Int = 0, init: inputGroupUI.() -> Unit) =
    ankoView({ inputGroupUI(it) }, theme, init)

class inputGroupUI(c: Context) : LinearLayout(c) {
    lateinit var textView: TextView
    lateinit var helperText: TextView
    lateinit var editText: EditText

    init {
        verticalLayout {
            lparams(width = matchParent)

            linearLayout {
                this@inputGroupUI.textView = textView {
                    text = "label"
                    textColor = ContextCompat.getColor(c, R.color.b20)
                    setTypeface(null, Typeface.BOLD)
                }
                this@inputGroupUI.helperText = textView {
                    text = ""
                    textColor = ContextCompat.getColor(c, R.color.rusty_red)
                    textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                }.lparams(width = matchParent) {
                    weight = 1F
                }
            }.lparams(width = matchParent) {
                bottomMargin = dip(8)
            }

            this@inputGroupUI.editText = editText {
                backgroundResource = R.drawable.edittext_radius_bg
                hint = "placeholder"
                inputType = InputType.TYPE_CLASS_TEXT
            }.lparams(width = matchParent) {
                bottomMargin = dip(16)
            }
        }
    }
}