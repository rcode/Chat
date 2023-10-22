package dev.rcode.android.domain.interactor

import dev.rcode.android.core.base.interactor.UseCase
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.model.Message
import dev.rcode.android.domain.repository.MessagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendMessage @Inject constructor(
    private val messagesRepository: MessagesRepository
):UseCase<Flow<Result<Message>>, SendMessage.Params>() {
    //suspend operator fun invoke(params: Params): Flow<Result<Message>> = messagesRepository.sendMessage(params.chatGroup, params.message)

    data class Params(val chatGroup: ChatGroup, val message: String)

    override suspend fun run(params: Params): Flow<Result<Message>> {
        return messagesRepository.sendMessage(params.chatGroup, params.message)
    }
}