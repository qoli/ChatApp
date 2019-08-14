package com.qoli.chatapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Recyclical
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.afollestad.recyclical.ViewHolder
import com.afollestad.recyclical.datasource.DataSource
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem

// anko
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

//layout
import kotlinx.android.synthetic.main.activity_chat.*

// socket
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.github.nkzawa.emitter.Emitter
import kotlinx.android.synthetic.main.chat_bubble.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class ActivityChat : AppCompatActivity() {

    private lateinit var mSocket: Socket
    private var dataSource: DataSource<Message> = dataSourceTypedOf()

    private fun connectChat() {
        this.mSocket = IO.socket("https://socket-io-chat.now.sh")
        mSocket.connect()
        mSocket.on(Socket.EVENT_CONNECT, connected)
    }

    private val connected = Emitter.Listener {
        doAsync {
            uiThread {
                println("Socket Connected")
            }
        }
    }

    private val disConnected = Emitter.Listener {
        this@ActivityChat.runOnUiThread(Runnable {
            toast("disconnected")
        })
    }

    private val onNewMessage = Emitter.Listener { args ->
        this@ActivityChat.runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val username: String
            val message: String
            try {
                username = data.getString("username").toString().trim()
                message = data.getString("message").toString().trim()
            } catch (e: JSONException) {
                return@Runnable
            }

            // add the message to view
            addMessage(username, message, false)
        })
    }

    private fun addMessage(name: String, message: String, isSend: Boolean) {
        println("send by name: $name")
        dataSource.add(Message(message, isSend))
        messageRecycler.scrollToPosition(dataSource.size() - 1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.chat_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.more -> {
            toast("more")
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val usernameString = intent.getStringExtra("username")
        supportActionBar?.title = usernameString
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.connectChat()

        mSocket.emit("add user", usernameString)
        mSocket.on("new message", onNewMessage)
        mSocket.on(Socket.EVENT_DISCONNECT, disConnected)

        // https://github.com/afollestad/recyclical

        // setup
        messageRecycler.setup {
            withDataSource(dataSource)
            withItem<Message, MessageViewHolder>(R.layout.chat_bubble) {
                onBind(::MessageViewHolder) { index, item ->

                    print("this index: $index")

                    messageLeft.text = item.message
                    messageRight.text = item.message
                    datetimeRight.text = getNow()
                    datetimeLeft.text = getNow()

                    if (item.isSend) {
                        leftBlock.isVisible = false
                        rightBlock.isVisible = true

                    } else {
                        leftBlock.isVisible = true
                        rightBlock.isVisible = false
                    }

                }
            }
        }

        sendButton.onClick {
            val textMessage: String = editText.text.toString().trim()
            if (textMessage != "") {
                addMessage("me", textMessage, true)
                editText.text = null
                mSocket.emit("new message", textMessage)
            } else {
                toast("你尚未輸入信息")
            }

        }

    }

    private fun getNow(): String {
        if (android.os.Build.VERSION.SDK_INT >= 24){
            return SimpleDateFormat("hh:mm a").format(Date())
        }else{
            var tms = Calendar.getInstance()
            return tms.get(Calendar.HOUR_OF_DAY).toString() + ":" + tms.get(Calendar.MINUTE).toString() +":" + tms.get(Calendar.SECOND).toString()
        }

    }
}

class MessageViewHolder(itemView: View) : ViewHolder(itemView) {
    val leftBlock: LinearLayout = itemView.findViewById(R.id.chat_left)
    val rightBlock: LinearLayout = itemView.findViewById(R.id.chat_right)
    val messageLeft: TextView = itemView.findViewById(R.id.text_message_left)
    val messageRight: TextView = itemView.findViewById(R.id.text_message_right)
    val datetimeLeft: TextView = itemView.findViewById(R.id.message_datetime_left)
    val datetimeRight: TextView = itemView.findViewById(R.id.message_datetime_right)
}

data class Message(
    var message: String,
    var isSend: Boolean
)

