package com.qoli.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.afollestad.recyclical.ViewHolder
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem

//layout
import kotlinx.android.synthetic.main.main_date.*

// anko
import org.jetbrains.anko.support.v4.intentFor

class FragmentDate : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.main_date, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // dataSourceTypedOf(...) here creates a DataSource<Person>
        val dataSource = dataSourceTypedOf(
            Dating("Date 1"),
            Dating("Date 2"),
            Dating("Date 3")
        )

        // setup{} is an extension method on RecyclerView
        recyclerView.setup {
            withDataSource(dataSource)
            withItem<Dating, DatingViewHolder>(R.layout.date_list) {
                onBind(::DatingViewHolder) { index, item ->
                    print(index)
                    name.text = item.name
                }
                onClick { index ->
                    print(index)
                    startActivity(intentFor<ActivityUserProfile>("username" to "${item.name}").addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                }
            }
        }
    }

}


class DatingViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name)
}

data class Dating(
    var name: String
)