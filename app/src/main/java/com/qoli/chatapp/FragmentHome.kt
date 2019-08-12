package com.qoli.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.afollestad.recyclical.ViewHolder
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

//
import kotlinx.android.synthetic.main.main_home.*

// anko
import org.jetbrains.anko.support.v4.intentFor

class FragmentHome : Fragment(), AnkoLogger {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.main_home, null)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // dataSourceTypedOf(...) here creates a DataSource<Person>
        val dataSource = dataSourceTypedOf(
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
            )
        )

        // setup{} is an extension method on RecyclerView
        recyclerView.setup {
            withDataSource(dataSource)
            withItem<Person, PersonViewHolder>(R.layout.home_list) {
                onBind(::PersonViewHolder) { index, item ->
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