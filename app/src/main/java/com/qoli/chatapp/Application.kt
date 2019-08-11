package com.qoli.chatapp

import android.app.Application
import android.os.StrictMode
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

//push
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

class Application : Application() {

    override fun onCreate() {

        FirebaseApp.initializeApp(this)

        val options = PusherOptions()
        options.setCluster("ap1");
        val pusher = Pusher("3da541dae5a2f6c9a64c", options)
        val channel = pusher.subscribe("my-channel")

        channel.bind("my-event") { channelName, eventName, data ->
            println("$channelName $eventName $data")
        }

        pusher.connect()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate()
    }
}
