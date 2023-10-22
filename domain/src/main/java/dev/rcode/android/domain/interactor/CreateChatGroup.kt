package dev.rcode.android.domain.interactor

import dev.rcode.android.core.base.interactor.UseCase
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.model.User
import dev.rcode.android.domain.repository.ChatGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateChatGroup @Inject constructor(
    private val chatGroupRepository: ChatGroupRepository
): UseCase<Flow<Result<ChatGroup>>, String>() {
    //suspend operator fun invoke(groupName: String): Flow<Result<ChatGroup>> = chatGroupRepository.createChatGroup(groupName)
    override suspend fun run(groupName: String): Flow<Result<ChatGroup>> {
        return chatGroupRepository.createChatGroup(groupName)
    }
}