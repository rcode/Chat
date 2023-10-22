package dev.rcode.android.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.rcode.android.data.local.db.entities.ChatGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createChatGroup(chatGroupEntity: ChatGroupEntity): Long

    @Update
    fun updateChatGroup(chatGroupEntity: ChatGroupEntity): Int

    @Query("SELECT * FROM chatgroupentity")
    fun getAllChatGroups(): Flow<List<ChatGroupEntity>>

    @Query("SELECT * FROM chatgroupentity WHERE chatGroupName LIKE :chatGroupName")
    fun getChatGroupDetails(chatGroupName: String): Flow<ChatGroupEntity>

    @Delete
    fun deleteChatGroup(chatGroupEntity: ChatGroupEntity): Int
}