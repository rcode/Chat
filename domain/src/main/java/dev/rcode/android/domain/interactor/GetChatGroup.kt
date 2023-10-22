package dev.rcode.android.domain.interactor

import dev.rcode.android.core.base.interactor.UseCase
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.repository.ChatGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatGroup @Inject constructor(
    private val chatGroupRepository: ChatGroupRepository
): UseCase<Flow<Result<ChatGroup>>, String>() {
    override suspend fun run(chatGroupName: String): Flow<Result<ChatGroup>> {
        return chatGroupRepository.getChatGroup(chatGroupName)
    }

}