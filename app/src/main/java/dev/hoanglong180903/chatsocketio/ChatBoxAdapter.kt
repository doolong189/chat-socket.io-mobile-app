package dev.hoanglong180903.chatsocketio

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.hoanglong180903.chatsocketio.databinding.ItemReceiverBinding
import dev.hoanglong180903.chatsocketio.databinding.ItemSenderBinding


class ChatBoxAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var MessageList: List<Message>? = null
    private val ITEM_SENT = 1
    private val ITEM_RECEIVE = 2
    override fun getItemCount(): Int {
        return MessageList!!.size
    }
    override fun getItemViewType(position: Int): Int {
        val m = MessageList!![position]
        return if ("user" == m.nickname) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SENT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sender, parent, false)
            SentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_receiver, parent, false)
            ReceiverViewHolder(view)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val m = MessageList!![position]
        if (holder is SentViewHolder) {
            holder.binding.nickname.text = m.nickname
            holder.binding.message.text = m.message
        } else if (holder is ReceiverViewHolder) {
            holder.binding.nickname.text = m.nickname
            holder.binding.message.text = m.message
        }
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemSenderBinding = ItemSenderBinding.bind(itemView)
    }

    class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemReceiverBinding = ItemReceiverBinding.bind(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(messages: List<Message>?) {
        this.MessageList = messages
        notifyDataSetChanged()
    }

}