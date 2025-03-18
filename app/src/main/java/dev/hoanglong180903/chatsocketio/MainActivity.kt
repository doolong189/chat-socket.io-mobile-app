package dev.hoanglong180903.chatsocketio

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.hoanglong180903.chatsocketio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    companion object{
        val NICKNAME = "usernickname"
        val ROOM = "roomname"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enterChat.setOnClickListener {
//            if(binding.nickname.getText().toString().isNotEmpty()){
//                val i = Intent(this@MainActivity, ChatBoxActivity::class.java)
//                i.putExtra(NICKNAME,binding.nickname.getText().toString());
//                startActivity(i);
//            }

            //new
            if(binding.nickname.text.toString().isNotEmpty() || binding.chanelId.text.toString().isNotEmpty()){
                val i = Intent(this@MainActivity, ChatBoxActivity::class.java)
                i.putExtra(NICKNAME,binding.nickname.text.toString())
                i.putExtra(ROOM,binding.chanelId.text.toString())
                startActivity(i);
            }
        }
    }
}