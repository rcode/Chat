package dev.rcode.android.domain.interactor

import dev.rcode.android.core.base.interactor.UseCase
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.model.User
import dev.rcode.android.domain.repository.ChatGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllChatGroups @Inject constructor(
    private val chatGroupRepository: ChatGroupRepository
): UseCase<Flow<Result<List<ChatGroup>>>, Any>() {
    //suspend operator fun invoke(): Flow<Result<List<ChatGroup>>> = chatGroupRepository.getAllChatGroups()
    override suspend fun run(params: Any): Flow<Result<List<ChatGroup>>> {
        return chatGroupRepository.getAllChatGroups()
    }
}