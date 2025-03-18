package dev.hoanglong180903.chatsocketio

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dev.hoanglong180903.chatsocketio.databinding.ActivityChatBoxBinding
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONException
import org.json.JSONObject


class ChatBoxActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatBoxBinding
    private var socket: Socket? = null
    private var Nickname: String? = null
    private var Roomname: String? = null
     var chatList: ArrayList<Message> = arrayListOf()
    lateinit var chatBoxAdapter: ChatBoxAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //======================== Chat room ========================//
//        Nickname = intent.extras!!.getString(MainActivity.NICKNAME)
//        Log.e("zzzz","${Nickname}")
//        try {
//            socket = IO.socket("http://192.168.52.197:6868")
//            //create connection
//            socket?.connect()
//            // emit the event join along side with the nickname
//            socket?.emit("join", Nickname);
//            Log.e("zzzz","socket connect")
//        } catch (e : Exception) {
//            e.printStackTrace();
//        }
//        socket?.on("userjoinedthechat") { args ->
//            runOnUiThread {
//                val data = args[0] as String
//                Toast.makeText(this@ChatBoxActivity, data, Toast.LENGTH_SHORT).show()
//                Log.e("zzzz","$data")
//                Log.e("zzzz","user joined the chat successfully")
//            }
//        }
//        chatList = ArrayList<Message>()
//        val mLayoutManager = LinearLayoutManager(applicationContext)
//        binding.mRecyclerView.setLayoutManager(mLayoutManager)
//        binding.mRecyclerView.setItemAnimator(DefaultItemAnimator())
//        binding.send.setOnClickListener {
//            if(binding.message.getText().toString().isNotEmpty()){
//                socket?.emit("messagedetection",Nickname,binding.message.getText().toString());
//                binding.message.setText("");
//            }
//        }
//        socket!!.on("message") { args ->
//            runOnUiThread {
//                val data = args[0] as JSONObject
//                try {
//                    //extract data from fired event
//                    val nickname = data.getString("senderNickname")
//                    val message = data.getString("message")
//                    // make instance of message
//                    val m = Message(nickname, message)
//                    //add the message to the messageList
//                    chatList.add(m)
//                    // add the new updated list to the adapter
//                    chatBoxAdapter = ChatBoxAdapter()
//                    chatBoxAdapter.submitList(chatList)
//                    // notify the adapter to update the recycler view
//                    binding.mRecyclerView.setAdapter(chatBoxAdapter)
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//        socket!!.on("userdisconnect") { args ->
//            runOnUiThread {
//                val data = args[0] as String
//                Toast.makeText(this@ChatBoxActivity, data, Toast.LENGTH_SHORT).show()
//            }
//        }


        //======================== Chat 1:1 ========================//
        Nickname = intent.extras!!.getString(MainActivity.NICKNAME)
        Roomname = intent.extras!!.getString(MainActivity.ROOM)
        Log.e("zzzz","${Nickname} \t ${Roomname}")
        try {
            socket = IO.socket("http://192.168.52.197:6868?channel_id=${Roomname}")
            socket?.connect()
            Log.e("zzzz","socket connect")
        } catch (e : Exception) {
            e.printStackTrace()
        }
        chatList = ArrayList<Message>()
        val mLayoutManager = LinearLayoutManager(applicationContext)
        binding.mRecyclerView.setLayoutManager(mLayoutManager)
        binding.mRecyclerView.setItemAnimator(DefaultItemAnimator())
        binding.send.setOnClickListener {
            if(binding.message.text.toString().isNotEmpty()){
                socket?.emit("message",binding.message.text.toString(), Nickname);
                binding.message.setText("")
            }
        }
        socket!!.on("message") { args ->
            runOnUiThread {
                val data = args[0] as JSONObject
                try {
                    val nickname = data.getString("id")
                    val message = data.getString("message")
                    val m = Message(nickname, message)
                    chatList.add(m)
                    chatBoxAdapter = ChatBoxAdapter()
                    chatBoxAdapter.submitList(chatList)
                    binding.mRecyclerView.setAdapter(chatBoxAdapter)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
        socket!!.on("close") { args ->
            runOnUiThread {
                val data = args[0] as String
                Toast.makeText(this@ChatBoxActivity, data, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        socket?.disconnect()
    }
}