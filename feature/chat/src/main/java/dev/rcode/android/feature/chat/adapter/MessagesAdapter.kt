package dev.rcode.android.feature.chat.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import androidx.recyclerview.widget.RecyclerView
import dev.rcode.android.domain.model.Message
import dev.rcode.android.feature.chat.R
import dev.rcode.android.feature.chat.databinding.MessageBinding

class MessagesAdapter(private val messages: MutableList<Message>, private val messageListener: MessageListener): RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.message, parent, false)
        val binding = MessageBinding.bind(view)
        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    inner class MessageViewHolder(private val binding: MessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.messageTextView.text = message.text
            binding.root.setOnLongClickListener {
                binding.root.setBackgroundColor(Color.LTGRAY)
                var position = messages.indexOf(message)
                messageListener.onMessageSelected(position)
                true
            }
            binding.root.setOnClickListener {
                binding.root.setBackgroundColor(Color.WHITE)
                messageListener.onMessageUnSelected()
            }
        }
    }

    fun setData(messages: MutableList<Message>) {
        this.messages.clear()
        this.messages.addAll(messages)
        notifyDataSetChanged()
    }

    interface MessageListener {
        fun onMessageSelected(position: Int)
        fun onMessageUnSelected()
    }
}