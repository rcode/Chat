package dev.rcode.android.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    var messageId: Long,
    var chatGroupId: Long,
    var text: String,
    var timestamp: Long,
    var userId: Long,
    var isRead: Boolean,
    var isPosted: Boolean
) {

    companion object {
        fun fromModel(messageModel: dev.rcode.android.domain.model.Message): MessageEntity {
            return MessageEntity(messageModel.messageId, messageModel.chatGroupId, messageModel.text, messageModel.timestamp, messageModel.userId, messageModel.isRead, messageModel.isPosted)
        }
    }

    fun toJson(): String {
        return  Gson().toJson(this)
    }

    fun toModel(): dev.rcode.android.domain.model.Message {
        return dev.rcode.android.domain.model.Message(this.messageId, this.chatGroupId, this.text, this.timestamp, this.userId, this.isRead, this.isPosted)
    }
}
