package dev.rcode.android.feature.chat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rcode.android.core.base.error.NoDataException
import dev.rcode.android.core.base.interactor.UseCase
import dev.rcode.android.core.ui.BaseViewModel
import dev.rcode.android.domain.interactor.CreateChatGroup
import dev.rcode.android.domain.interactor.GetAllChatGroups
import dev.rcode.android.domain.interactor.GetThisUser
import dev.rcode.android.domain.interactor.ToggleMuteForChatGroup
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.model.User
import dev.rcode.android.feature.chat.adapter.ChatListAdapter
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListFragmentViewModel @Inject constructor(
    private val getThisUser: GetThisUser,
    private val createChatGroup: CreateChatGroup,
    private val getAllChatGroupsUseCase: GetAllChatGroups,
    private val toggleMuteForChatGroup: ToggleMuteForChatGroup
) : BaseViewModel<ChatListUIState>() {

    var chatGroupsLiveData = MutableLiveData<List<ChatGroup>>()
    var userLiveData = MutableLiveData<User>()
    var error = MutableLiveData<Throwable>()
    var message = MutableLiveData<String>()
    var goToRegistrationScreen = MutableLiveData(false)
    var selectedChatGroup = MutableLiveData<ChatGroup?>(null)
    //var selectedChatGroup: ChatGroup? = null

    fun getInitialData() {
        getUserData()
    }

    // check if user object is not null
    private fun getUserData() = getThisUser(UseCase.None()) {
        viewModelScope.launch {
            it.map { result ->
                result.fold({ user ->
                    userLiveData.postValue(user)
                    goToRegistrationScreen.postValue(false)
                    getAllChatGroups()
                }, { e ->
                    if(e is NoDataException)
                        goToRegistrationScreen.postValue(true)
                    else
                        error.postValue(e)

                })
            }.catch { throwable ->
                showError(throwable)
            }.collect()
        }
    }

    fun createNewChatGroup(chatGroupName: String) {

        createChatGroup(chatGroupName) {
            viewModelScope.launch {
                it.catch { throwable ->
                    showError(throwable)
                }.collect { result ->
                    result.fold({
                        showMessage("Chatgroup created successfully")
                    }, {
                        showError(it)
                    })
                }
            }
        }
    }

    private fun getAllChatGroups() {
        getAllChatGroupsUseCase(UseCase.None()) {
            viewModelScope.launch {
                it.catch {
                    Log.e(this@ChatListFragmentViewModel::class.java.simpleName, it.message ?: "")
                }.collect {
                    it.fold({ chatGroupList ->
                        chatGroupsLiveData.postValue(chatGroupList)
                        chatGroupList.forEach { chatGroup ->
                            Log.d(this@ChatListFragmentViewModel::class.java.simpleName, chatGroup.chatGroupName!!)
                        }
                        // ChatListUIState.ChatGroupsRetrieved(it)
                    }, { throwable ->
                        error.postValue(throwable)
                        // ChatListUIState.Error("Group Not created")
                    })
                }
            }
        }
    }

    fun toggleMute(chatGroup: ChatGroup) {
        toggleMuteForChatGroup(chatGroup) {
            viewModelScope.launch {
                it.catch {
                    Log.d(this@ChatListFragmentViewModel::class.java.simpleName, it.message?: "some error occurred")
                }.collect {
                    it.fold({
                        Log.d(this@ChatListFragmentViewModel::class.java.simpleName, "")
                    }, {
                        Log.d(this@ChatListFragmentViewModel::class.java.simpleName, it.message?: "some error occurred")
                    })
                }
            }
        }
    }

    private fun showError(throwable: Throwable) {
        error.postValue(throwable)
        throwable.message?.let { error ->
            Log.e(this::class.java.simpleName, error)
        }
    }

    private fun showMessage(message: String) {
        this.message.postValue(message)
        Log.d(this::class.java.simpleName, message)
    }

}