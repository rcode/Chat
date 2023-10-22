package dev.rcode.android.feature.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rcode.android.core.ui.BaseViewModel
import dev.rcode.android.domain.interactor.CreateChatGroup
import dev.rcode.android.domain.interactor.DeleteMessage
import dev.rcode.android.domain.interactor.GetAllChatGroups
import dev.rcode.android.domain.interactor.GetAllMessages
import dev.rcode.android.domain.interactor.GetChatGroup
import dev.rcode.android.domain.interactor.GetThisUser
import dev.rcode.android.domain.interactor.SendMessage
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.model.Message
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessage: SendMessage,
    private val deleteMessage: DeleteMessage,
    private val getAllMessages: GetAllMessages,
    private val getChatGroup: GetChatGroup
) : BaseViewModel<ChatListUIState>() {

    var messagesLiveData = MutableLiveData(mutableListOf<Message>())

    var chatGroup = MutableLiveData<ChatGroup>()

    fun sendMessage(message: String) {

        sendMessage(SendMessage.Params(this@ChatViewModel.chatGroup.value!!, message)) {
            viewModelScope.launch {
                it.catch {
                    Log.e(this::class.java.simpleName, it.message?: "Some error occurred")
                }.collect {
                    it.fold({
                        getMessages()
                    }, {
                        Log.e(this::class.java.simpleName, it.message?: "Some error occurred")
                    })
                }
            }
        }
    }

    fun getMessages() {
        getAllMessages(chatGroup.value!!) {
            viewModelScope.launch {
                it.catch {
                    Log.e(this::class.java.simpleName, it.message?: "Some error occurred")
                }.collect {
                    it.fold({ messageList ->
                        messagesLiveData.value?.clear()
                        messagesLiveData.postValue(messageList)
                    }, {
                        Log.e(this::class.java.simpleName, it.message?: "Some error occurred")
                    })
                }
            }
        }
    }

    fun getChatGroupInfo(chatGroupName: String) {
        getChatGroup(chatGroupName) {
            viewModelScope.launch {
                it.catch {
                    Log.e(this::class.java.simpleName, it.message?: "Some error occurred")
                }.collect {
                    it.fold({ chatGroupInfo ->
                            chatGroup.postValue(chatGroupInfo)
                    },{
                        Log.e(this::class.java.simpleName, it.message?: "Some error occurred")
                    })
                }
            }
        }
    }

    fun deleteSelectedMessage(message: Message) {
        deleteMessage(message) {
            viewModelScope.launch {
                it.catch {
                    Log.e(this::class.java.simpleName, it.message?: "Some error occurred")
                }.collect {
                    it.fold({
                        Log.i(this::class.java.simpleName, "Message deleted successfully")
                    },{
                        Log.e(this::class.java.simpleName, it.message?: "Some error occurred")
                    })
                }
            }
        }
    }
}