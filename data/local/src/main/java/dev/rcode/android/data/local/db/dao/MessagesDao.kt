package dev.rcode.android.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.rcode.android.data.local.db.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(messageEntity: MessageEntity): Long

    @Query("SELECT * FROM messageentity WHERE messageentity.chatGroupId LIKE :groupId")
    fun getMessages(groupId: Long): Flow<MutableList<MessageEntity>>

    @Delete
    fun deleteMessage(messageEntity: MessageEntity): Int

}