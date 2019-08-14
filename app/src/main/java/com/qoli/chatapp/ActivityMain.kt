package com.qoli.chatapp

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.orhanobut.hawk.Hawk
import com.qoli.chatapp.AppString.StoreKeyName
import com.qoli.chatapp.Setting.SettingGender
import com.qoli.chatapp.Setting.SettingMember
import org.jetbrains.anko.*


class ActivityMain : AppCompatActivity(), AnkoLogger {

    // ↓ View 生命週期
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        //startActivity<SettingMember>()
        //return

        if (!userStatus()) {
            return
        }

        // BottomNavigationView
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // 切換主頁面
        bottomNavSwitchFragment(FragmentHome())

        info { "activity_main onCreate()" }

        methodRequiresPermissions()
        getPushToken()

    }

    // ↑ View 生命週期

    // ↓ BottomNavigationView
    private fun bottomNavSwitchFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frament_container, fragment).commit()
            return true
        }
        return false
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_1 -> {
                return@OnNavigationItemSelectedListener bottomNavSwitchFragment(FragmentHome())
            }
            R.id.navigation_2 -> {
                return@OnNavigationItemSelectedListener bottomNavSwitchFragment(FragmentDate())
            }
            R.id.navigation_3 -> {
                return@OnNavigationItemSelectedListener bottomNavSwitchFragment(FragmentMessage())
            }

        }
        false
    }

    // ↑ BottomNavigationView

    // ↓ 權限請求
    private fun methodRequiresPermissions() = runWithPermissions(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.INTERNET
    ) {
        info { "methodRequiresPermissions" }
    }
    // ↑ 權限

    /**
     *
     * @return true, 已登入；false 未登入
     */

    private fun userStatus(): Boolean {
        val key = StoreKeyName().ifLogin()

        if (Hawk.contains(key)) {
            if (Hawk.get(key)) {
                //已登入
                return true
            } else {
                startActivity<ActivityWelcome>()
                return false
            }
        } else {
            startActivity<ActivityWelcome>()
            return false
        }
    }

    private fun getPushToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { result ->
            val token = result.token
            toast(token)
        }
    }
}
