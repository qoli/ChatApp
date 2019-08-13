package com.qoli.chatapp.function

// anko
import android.content.Context
import org.jetbrains.anko.*

//
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.qoli.chatapp.R

class AppGMS(instance: Context) : AMapLocationListener,AnkoLogger {

    private var thisContext: Context = instance
    private var mLocationClient: AMapLocationClient
    private var amapLocation: AMapLocation? = null
    private var aoiName: String = ""

    init {

        info { "... init" }

        val mLocationOption = AMapLocationClientOption()
        mLocationOption.isNeedAddress = true
        mLocationOption.isOnceLocation = true // 只定位一次

        mLocationClient = AMapLocationClient(instance)
        mLocationClient.setLocationOption(mLocationOption)
        mLocationClient.setLocationListener(this)

        start()
    }

    private fun start() {
        //启动定位
        info { "... start" }
        mLocationClient.startLocation()
    }

    fun stop() {
        mLocationClient.stopLocation()
    }

    fun getClient() : AMapLocationClient {
        return mLocationClient
    }

    fun getName() : String {
        info { "... get Name $aoiName" }
        return aoiName
    }

    override fun onLocationChanged(amapLocation: AMapLocation?) {
        info { "onLocationChanged() errorCode: ${amapLocation?.errorCode} | ${amapLocation?.latitude}, ${amapLocation?.longitude}" }
        info { "${amapLocation?.address} ${amapLocation?.aoiName}" }

        aoiName = amapLocation?.aoiName.toString()
    }
}