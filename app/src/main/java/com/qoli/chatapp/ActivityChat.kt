package com.qoli.chatapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Recyclical
import android.view.View
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
import org.json.JSONException
import org.json.JSONObject


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

                    messageTextViewLeft.text = item.message
                    messageTextViewRight.text = item.message

                    if (item.isSend) {
                        messageTextViewLeft.visibility = View.GONE
                        messageTextViewRight.visibility = View.VISIBLE
                    } else {
                        messageTextViewLeft.visibility = View.VISIBLE
                        messageTextViewRight.visibility = View.GONE
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
}

class MessageViewHolder(itemView: View) : ViewHolder(itemView) {
    val messageTextViewLeft: TextView = itemView.findViewById(R.id.text_message_left)
    val messageTextViewRight: TextView = itemView.findViewById(R.id.text_message_right)
}

data class Message(
    var message: String,
    var isSend: Boolean
)