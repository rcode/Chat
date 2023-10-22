package dev.rcode.android.domain.interactor

import dev.rcode.android.core.base.interactor.UseCase
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.repository.ChatGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToggleMuteForChatGroup @Inject constructor(
    private val chatGroupRepository: ChatGroupRepository
): UseCase<Flow<Result<ChatGroup>>, ChatGroup>() {
    //suspend operator fun invoke(chatGroup: ChatGroup): Flow<Result<ChatGroup>> = chatGroupRepository.toggleMuteForChatGroup(chatGroup)
    override suspend fun run(chatGroup: ChatGroup): Flow<Result<ChatGroup>> {
        return chatGroupRepository.toggleMuteForChatGroup(chatGroup)
    }
}