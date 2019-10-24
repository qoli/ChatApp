package com.qoli.chatapp

import android.app.Application
import android.os.StrictMode
import android.util.Log
import com.google.firebase.FirebaseApp
import com.orhanobut.hawk.Hawk
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent



class MainApplication : Application() {

    val TAG = "mPushAgent"
    
    override fun onCreate() {

        // FCM
        FirebaseApp.initializeApp(this)

        // storage
        Hawk.init(this).build()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // 日誌過多，暫時注釋

//        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
//        UMConfigure.init(this, "5d5d946a4ca357eaaa000291", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "ddc3daddec6d40aafab5112622d7f81d");
//        // 获取消息推送代理示例
//        val mPushAgent = PushAgent.getInstance(this)
//        // 注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(object : IUmengRegisterCallback {
//            override fun onSuccess(deviceToken: String) {
//                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
//                Log.i(TAG, "注册成功：deviceToken：-------->  $deviceToken")
//            }
//            override fun onFailure(s: String, s1: String) {
//                Log.e(TAG, "注册失败：-------->  s:$s,s1:$s1")
//            }
//        })
        
        super.onCreate()
    }
}
