package dev.rcode.android.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.rcode.android.data.local.db.dao.ChatGroupDao
import dev.rcode.android.data.local.db.dao.MessagesDao
import dev.rcode.android.data.local.db.dao.UserDao
import dev.rcode.android.data.local.db.entities.ChatGroupEntity
import dev.rcode.android.data.local.db.entities.MessageEntity
import dev.rcode.android.data.local.db.entities.UserEntity

@Database(version = 1, entities = [UserEntity::class, MessageEntity::class, ChatGroupEntity::class], exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessagesDao
    abstract fun chatGroupDao(): ChatGroupDao

}