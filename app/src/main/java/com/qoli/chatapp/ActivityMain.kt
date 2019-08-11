package com.qoli.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

//layout
import kotlinx.android.synthetic.main.activity_main.*

import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity

// anko
import org.jetbrains.anko.*

// Recyclical
import android.view.View
import com.afollestad.recyclical.ViewHolder
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem

//
import com.google.firebase.iid.FirebaseInstanceId


class ActivityMain : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_1 -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_2 -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_3 -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.user_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.search -> {
            toast("search")
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // BottomNavigationView
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // https://material.io

        this.getMyToken()

        // dataSourceTypedOf(...) here creates a DataSource<Person>
        val dataSource = dataSourceTypedOf(
            Person("Aidan", 24),
            Person("Nina", 24),
            Person("yaya", 18)
        )

        // setup{} is an extension method on RecyclerView
        recyclerView.setup {
            withDataSource(dataSource)
            withItem<Person, PersonViewHolder>(R.layout.main_user_list) {
                onBind(::PersonViewHolder) { index, item ->
                    print(index)
                    name.text = item.name
                    age.text = item.age.toString()
                }
                onClick { index ->
                    print(index)
                    startActivity(intentFor<ActivityUserProfile>("username" to "${item.name}").addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                }
            }
        }

    }

    private fun getMyToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { result ->
            val token = result.token
            toast(token)
        }
    }

}

class PersonViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.text_name)
    val age: TextView = itemView.findViewById(R.id.text_age)
}

data class Person(
    var name: String,
    var age: Int
)