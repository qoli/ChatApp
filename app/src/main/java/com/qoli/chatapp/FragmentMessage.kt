package com.qoli.chatapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.orhanobut.hawk.Hawk
import com.qoli.chatapp.AppString.StoreKeyName
import kotlinx.android.synthetic.main.main_message.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity

class FragmentMessage : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_message,null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout.onClick {
            Hawk.put(StoreKeyName().ifLogin(), false)
            startActivity<ActivityWelcome>()
        }

    }
}