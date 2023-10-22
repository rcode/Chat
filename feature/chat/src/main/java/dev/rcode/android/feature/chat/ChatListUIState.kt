package dev.rcode.android.feature.chat

import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.model.User

sealed class ChatListUIState {

    object Loading : ChatListUIState()
    data class UserRetrieved(val user: User) : ChatListUIState()
    object UserNotAvailable : ChatListUIState()
    data class Success(val channels: List<ChatGroup>) : ChatListUIState()
    data class Error(val message: String) : ChatListUIState()
    data class ChatGroupCreated(val chatGroup: ChatGroup) : ChatListUIState()
    data class ChatGroupsRetrieved(val chatGroups: List<ChatGroup>) : ChatListUIState()
}