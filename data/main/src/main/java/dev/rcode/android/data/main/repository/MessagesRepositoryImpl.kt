package dev.rcode.android.data.main.repository

import com.google.firebase.database.FirebaseDatabase
import dev.rcode.android.core.base.error.DataBaseException
import dev.rcode.android.data.local.db.ChatDatabase
import dev.rcode.android.data.local.db.entities.MessageEntity
import dev.rcode.android.domain.model.ChatGroup
import dev.rcode.android.domain.model.Message
import dev.rcode.android.domain.repository.ChatGroupRepository
import dev.rcode.android.domain.repository.MessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(
private val db: ChatDatabase,
private val fireBaseRealTimeDatabase: FirebaseDatabase
): MessagesRepository {


    override suspend fun getAllMessages(chatGroup: ChatGroup): Flow<Result<MutableList<Message>>> {
        return db.messageDao().getMessages(chatGroup.chatGroupId).map {
            Result.success(it.map { messageEntity ->
                messageEntity.toModel()
            }.toMutableList())
        }
    }

    override suspend fun sendMessage(chatGroup: ChatGroup, message: String): Flow<Result<Message>> {
        var messageEntity = MessageEntity(0, chatGroup.chatGroupId, message, System.currentTimeMillis(), userId = 1, isRead = true, isPosted = false)
        var result = db.messageDao().insertMessage(messageEntity)
        return flow {
            if(result != -1L) {
                messageEntity.messageId = result
                emit(Result.success(messageEntity.toModel()))
            } else {
                emit(Result.failure(DataBaseException()))
            }
        }
    }

    override suspend fun deleteMessage(message: Message): Flow<Result<Boolean>> {
        var result = db.messageDao().deleteMessage(MessageEntity.fromModel(message))
        return flow {
            if(result != -1)
                Result.success(true)
            else
                Result.failure(DataBaseException())
        }
    }

}