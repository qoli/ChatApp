package com.qoli.chatapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity

// anko
import org.jetbrains.anko.*

// Fragment
import androidx.fragment.app.Fragment

//
import com.google.firebase.iid.FirebaseInstanceId
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.google.android.gms.common.util.MapUtils




class ActivityMain : AppCompatActivity(), AnkoLogger, LocationListener {
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

        this.getGMS()

        this.getPushToken()

        // 切換主頁面
        loadFragment(FragmentHome())
    }

    override fun onResume() {
        super.onResume()
    }
    // ↑ View 生命週期


    // ↓ GMS Location

    private lateinit var locationManager: LocationManager

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("MissingPermission")
    private fun getGMS() = runWithPermissions(Manifest.permission.ACCESS_FINE_LOCATION) {

        val crit = Criteria()
        crit.accuracy = Criteria.ACCURACY_FINE
        crit.powerRequirement = Criteria.POWER_LOW
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val provider = locationManager.getBestProvider(crit,true)

        info {"provider: $provider"}


        locationManager.requestLocationUpdates(provider, 0, 0F, this)

        this.getLastKnownLocation()

        //locationManager.removeUpdates(this)
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() = runWithPermissions(Manifest.permission.ACCESS_COARSE_LOCATION) {
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null)
        var location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        info { "getLastKnownLocation GPS $location" }
        if (location == null) {
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null)
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            info { "getLastKnownLocation Network $location" }
        }

    }

    override fun onLocationChanged(location: Location) {
        info { "Listener RUNNING" }
        info { "onLocationChanged $location" }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        info { "onStatusChanged $provider $status" }
    }

    override fun onProviderEnabled(provider: String?) {
        info { "onProviderEnabled $provider" }
    }

    override fun onProviderDisabled(provider: String?) {
        info { "onProviderDisabled $provider" }
    }

    // ↑ GMS Location

    private fun getPushToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { result ->
            val token = result.token
            toast(token)
        }
    }
}