package com.qoli.chatapp

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast


class ActivityMain : AppCompatActivity(), AnkoLogger {

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frament_container, fragment).commit()
            return true
        }
        return false
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_1 -> {
                return@OnNavigationItemSelectedListener loadFragment(FragmentHome())
            }
            R.id.navigation_2 -> {
                return@OnNavigationItemSelectedListener loadFragment(FragmentDate())
            }
            R.id.navigation_3 -> {
                return@OnNavigationItemSelectedListener loadFragment(FragmentMessage())
            }

        }
        false
    }

    // ↓ View 生命週期
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // hide ActionBar
        supportActionBar?.hide()

        // BottomNavigationView
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        info { "activity_main onCreate()" }

        methodRequiresPermissions()
        getPushToken()

        // 切換主頁面
        loadFragment(FragmentHome())
    }

    override fun onResume() {
        super.onResume()
    }
    // ↑ View 生命週期

    // ↓ 權限請求
    private fun methodRequiresPermissions() = runWithPermissions(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.INTERNET
    ) {
        info { "methodRequiresPermissions" }
    }
    // ↑ 權限

    private fun getPushToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { result ->
            val token = result.token
            toast(token)
        }
    }
}
