package com.qoli.chatapp.Setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.qoli.chatapp.ActivityMain
import com.qoli.chatapp.R
import kotlinx.android.synthetic.main.setting_member.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SettingMember : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_member)
        supportActionBar?.title = "饭饭会员"

        val myContext = this

        PayButton.onClick {
            MaterialDialog(myContext, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                cornerRadius(16f)
                title(R.string.pay_dialog_title)
                negativeButton(R.string.cancel)
                listItems(R.array.payWay) { _, index, text ->
                    pay(index, text)
                }
                lifecycleOwner(this@SettingMember)
            }
        }

        PassButton.onClick {
            startActivity<SettingDone>()

        }
    }

    private fun pay(index: Int, text: String) {
        toast("Selected item $text at index $index")
    }
}
