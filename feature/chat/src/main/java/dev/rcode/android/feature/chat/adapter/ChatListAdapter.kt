package dev.rcode.android.feature.chat.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.feature.chat.ChatListFragmentViewModel
import dev.rcode.android.feature.chat.R
import dev.rcode.android.feature.chat.databinding.ItemChatGroupBinding

class ChatListAdapter(private var chatGroups: MutableList<ChatGroup>, val chatGroupListener: ChatGroupListener, private val selectedChatGroup: MutableLiveData<ChatGroup?>): RecyclerView.Adapter<ChatListAdapter.ChatGroupViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatListAdapter.ChatGroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_chat_group, parent, false)
        val binding = ItemChatGroupBinding.bind(view)
        return ChatGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatListAdapter.ChatGroupViewHolder, position: Int) {
        holder.bind(chatGroups[position])
    }

    override fun getItemCount(): Int {
        return chatGroups.size
    }

    fun setData(chatGroups: List<ChatGroup>) {
        this.chatGroups.clear()
        this.chatGroups.addAll(chatGroups)
        notifyDataSetChanged()
    }

    inner class ChatGroupViewHolder(private val binding: ItemChatGroupBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(chatGroup: ChatGroup) {
            binding.textviewChatGroupName.text = chatGroup.chatGroupName

            if(chatGroup.isMuted)
                binding.muteImageView.visibility = View.VISIBLE
            else
                binding.muteImageView.visibility = View.INVISIBLE

            binding.root.setOnLongClickListener {
                chatGroupListener.onChatGroupSelected(chatGroup.chatGroupName)
                binding.root.setBackgroundColor(Color.LTGRAY)
                true
            }
            binding.root.setOnClickListener {
                if(selectedChatGroup.value?.chatGroupName == chatGroup.chatGroupName) {
                    binding.root.setBackgroundColor(Color.WHITE)
                    chatGroupListener.onChatUnSelected()
                } else
                    chatGroupListener.onChatGroupClicked(chatGroup.chatGroupName)
            }

        }
    }

    interface ChatGroupListener{
        fun onChatGroupClicked(groupName: String)
        fun onChatGroupSelected(groupName: String)

        fun onChatUnSelected()
    }
}