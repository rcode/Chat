package dev.rcode.android.domain.interactor

import dev.rcode.android.core.base.interactor.UseCase
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.model.Message
import dev.rcode.android.domain.repository.MessagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMessages @Inject constructor(
    private val messagesRepository: MessagesRepository
):UseCase<Flow<Result<MutableList<Message>>>, ChatGroup>() {
    //suspend operator fun invoke(chatGroup: ChatGroup): Flow<Result<List<Message>>> = messagesRepository.getAllMessages(chatGroup)
    override suspend fun run(chatGroup: ChatGroup): Flow<Result<MutableList<Message>>> {
        return messagesRepository.getAllMessages(chatGroup)
    }
}