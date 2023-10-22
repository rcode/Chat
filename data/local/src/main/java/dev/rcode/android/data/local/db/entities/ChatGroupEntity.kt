package dev.rcode.android.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import org.jetbrains.annotations.NotNull

@Entity
data class ChatGroupEntity(
    @PrimaryKey(autoGenerate = true)
    val chatGroupId: Long,
    val chatGroupName: String,
    val createdOn: Long,
    val isMuted: Boolean
) {
    companion object {
        fun fromModel(chatGroupModel: dev.rcode.android.domain.model.ChatGroup): ChatGroupEntity {
            return ChatGroupEntity(chatGroupModel.chatGroupId, chatGroupModel.chatGroupName, chatGroupModel.createdOn, chatGroupModel.isMuted)
        }
    }

    fun toJson(): String {
        return  Gson().toJson(this)
    }

    fun toModel(): dev.rcode.android.domain.model.ChatGroup {
        return dev.rcode.android.domain.model.ChatGroup(this.chatGroupId, this.chatGroupName, this.createdOn, this.isMuted)
    }
}
