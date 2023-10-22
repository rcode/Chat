package dev.rcode.android.feature.chat

import dev.rcode.android.domain.model.ChatGroup

sealed class ChatUIState {

    object Loading : ChatUIState()

    data class ChatMessagesRetrieved(val chatGroup: ChatGroup) : ChatUIState()

    data class Error(val message: String) : ChatUIState()
}