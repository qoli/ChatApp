package com.qoli.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.recyclical.ViewHolder
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.datasource.emptyDataSource
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.google.android.material.tabs.TabLayout
import com.orhanobut.hawk.Hawk
import com.qoli.chatapp.AppFunction.AppGMS
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_home.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity

class FragmentHome : Fragment(), AnkoLogger, SwipeRefreshLayout.OnRefreshListener, TabLayout.OnTabSelectedListener {

    // ↓ View

    private var myView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_home, null)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myView = view

        myAvatar()
        gmsInit()

        myAvatar.onClick {
            startActivity<ActivitySetting>()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
    }

    // ↑ View

    private fun myAvatar() {
        Picasso.get().load("https://pic.qqtn.com/up/2019-7/2019072508412719909.jpg").into(myAvatar)
    }

    // ↓ GMS

    private var gms: AppGMS? = null

    private fun gmsInit() {

//        if (Hawk.contains("myLocationTextView")) {
//            val name: String = Hawk.get("myLocationTextView")
//
//            if (!name.isNullOrEmpty()) {
//                info { "gmsInit() name: $name" }
//                myLocationTextView.text = name
//                personInit()
//            }
//        }

        if (gms == null) {
            gms = AppGMS(activity!!.application)
            getLocation(gms!!)
        }

        myLocationTextView.onClick {
            getLocation(gms!!)
        }
    }

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    private fun getLocation(gms: AppGMS) {
        myLocationTextView?.text = getString(R.string.lookingForLocation)

        mHandler = Handler()
        mRunnable = Runnable {
            val name = gms.getName()

            if (name == "") {
                myLocationTextView.text = getString(R.string.locationError)
            } else {
                Hawk.put("myLocationTextView", name)
                myLocationTextView.text = name
                personInit()
            }
        }

        mHandler.postDelayed(mRunnable, 1200)
    }

    // ↑ GMS

    // ↓ pageSetup

    override fun onTabReselected(p0: TabLayout.Tab?) {
//        info { p0 }
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
//        info { p0 }
    }

    override fun onTabSelected(item: TabLayout.Tab) {
        info { "onTabSelected: ${item.position}" }
        myLocationTextView.isVisible = item.position == 0
        personDataSource.removeAt(0)
    }

    override fun onRefresh() {
        Handler().postDelayed({
            personInit()
        }, 3000)
    }

    private fun personInit() {
        swiperefresh.isRefreshing = true
        tabLayout.addOnTabSelectedListener(this)
        swiperefresh.setColorSchemeResources(R.color.colorPrimary)
        swiperefresh.setOnRefreshListener(this)

        personData()
        personSetup()
        swiperefresh.isRefreshing = false
    }

    private var personDataSource = emptyDataSource()

    private fun personData() {

        personDataSource = dataSourceTypedOf(
            Person(
                "https://pic.qqtn.com/up/2018-8/2018081409360724634.jpg",
                "玩火小仙女",
                "<span style=\"color: #ff83b6;\">[今天约会]</span> 26, 文化传播", "1.38km", true, false, false, false, false
            ),
            Person(
                "https://pic.qqtn.com/up/2019-1/2019011817385224678.jpg",
                "荷塘月色",
                "<span style=\"color: #ff83b6;\">[明天约会]</span> 32, OL",
                "0.48km · 12 分钟前",
                false,
                false,
                true,
                true,
                false
            ),
            Person(
                "https://pic.qqtn.com/up/2019-5/2019050718292867806.jpg",
                "思雨",
                "22, 学生",
                "3.22km · 15 分钟前",
                false,
                true,
                false,
                true,
                false
            ),
            Person(
                "",
                "无头像",
                "<span style=\"color: #ff83b6;\">[今天约会]</span> 22, 学生",
                "10.86km · 1 小時前",
                false, false, false, false, true
            ),
            Person(
                "",
                "网名很长有什么用，名字没人看",
                "<span style=\"color: #ff83b6;\">[今天约会]</span> 22, 学生",
                "10.86km · 1 小時前",
                false, false, false, false, true
            ),
            Person(
                "https://pic.qqtn.com/up/2019-5/2019050718292867806.jpg",
                "思雨",
                "22, 学生",
                "3.22km · 15 分钟前",
                false,
                true,
                false,
                true,
                false
            ),
            Person(
                "",
                "无头像",
                "<span style=\"color: #ff83b6;\">[今天约会]</span> 22, 学生",
                "10.86km · 1 小時前",
                false, false, false, false, true
            ),
            Person(
                "",
                "网名很长有什么用，名字没人看",
                "<span style=\"color: #ff83b6;\">[今天约会]</span> 22, 学生",
                "10.86km · 1 小時前",
                false, false, false, false, true
            )
        )
    }

    private fun personSetup() {
        recyclerView.setup {
            withDataSource(personDataSource)
            withItem<Person, PersonViewHolder>(R.layout.home_list) {
                onBind(::PersonViewHolder) { index, item ->
                    //print(index)
                    if (!item.avatar.isNullOrEmpty()) {
                        Picasso.get().load("${item.avatar}").into(avatar)
                    }
                    if (item.isOnline) {
                        datetime.setTextAppearance(R.style.StatusText1Online)
                    }
                    name.text = item.name
                    description.text = Html.fromHtml("${item.description}", Html.FROM_HTML_MODE_LEGACY)
                    datetime.text = item.datetime
                    isMicWait.isVisible = item.isMicWait
                    isMicBusy.isVisible = item.isMicBusy
                    isReal.isVisible = item.isReal
                    isVip.isVisible = item.isVip
                }
                onClick { index ->
                    info("onClick: $index")
                    startActivity(intentFor<ActivityUserProfile>("username" to "${item.name}").addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                }
            }
        }
    }

    // ↑ pageSetup
}


class PersonViewHolder(itemView: View) : ViewHolder(itemView) {
    var avatar: ImageView = itemView.findViewById(R.id.avatar)
    val name: TextView = itemView.findViewById(R.id.name)
    val description: TextView = itemView.findViewById(R.id.description)
    val datetime: TextView = itemView.findViewById(R.id.date)
    val isMicWait: ImageView = itemView.findViewById(R.id.isMicWait)
    val isMicBusy: ImageView = itemView.findViewById(R.id.isMicBusy)
    val isReal: ImageView = itemView.findViewById(R.id.isReal)
    val isVip: ImageView = itemView.findViewById(R.id.isVip)
}

data class Person(
    var avatar: String,
    var name: String,
    var description: String,
    var datetime: String,
    var isOnline: Boolean,
    var isMicWait: Boolean,
    var isMicBusy: Boolean,
    var isReal: Boolean,
    var isVip: Boolean
)