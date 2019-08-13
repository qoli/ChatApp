package com.qoli.chatapp

import android.app.Application
import android.os.StrictMode
import com.google.firebase.FirebaseApp
import com.orhanobut.hawk.Hawk

class MainApplication : Application() {

    override fun onCreate() {

        // FCM
        FirebaseApp.initializeApp(this)

        // storage
        Hawk.init(this).build()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate()
    }
}
