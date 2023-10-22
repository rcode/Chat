package dev.rcode.android.domain.repository

import dev.rcode.android.domain.model.ChatGroup
import kotlinx.coroutines.flow.Flow

interface ChatGroupRepository {

    suspend fun createChatGroup(groupName: String): Flow<Result<ChatGroup>>

    suspend fun deleteChatGroup(chatGroup: ChatGroup): Flow<Result<Boolean>>

    suspend fun getAllChatGroups(): Flow<Result<List<ChatGroup>>>

    suspend fun toggleMuteForChatGroup(chatGroup: ChatGroup): Flow<Result<ChatGroup>>

    suspend fun getChatGroup(chatGroupName: String): Flow<Result<ChatGroup>>
}