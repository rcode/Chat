package dev.rcode.android.domain.repository

import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {

    suspend fun getAllMessages(chatGroup: ChatGroup): Flow<Result<MutableList<Message>>>
    suspend fun sendMessage(chatGroup: ChatGroup, message: String): Flow<Result<Message>>
    suspend fun deleteMessage(message: Message): Flow<Result<Boolean>>
}