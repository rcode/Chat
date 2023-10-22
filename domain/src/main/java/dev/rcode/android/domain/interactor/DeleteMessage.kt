package dev.rcode.android.domain.interactor

import dev.rcode.android.core.base.interactor.UseCase
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.model.Message
import dev.rcode.android.domain.repository.ChatGroupRepository
import dev.rcode.android.domain.repository.MessagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteMessage  @Inject constructor(
    private val messagesRepository: MessagesRepository
): UseCase<Flow<Result<Boolean>>, Message>() {
    //suspend operator fun invoke(message: Message): Flow<Result<Boolean>> = messagesRepository.deleteMessage(message)
    override suspend fun run(message: Message): Flow<Result<Boolean>> {
        return messagesRepository.deleteMessage(message)
    }
}