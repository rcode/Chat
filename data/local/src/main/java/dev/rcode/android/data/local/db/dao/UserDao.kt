package dev.rcode.android.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.rcode.android.data.local.db.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    fun createUser(userEntity: UserEntity): Long

    @Query("SELECT * FROM userentity WHERE userId = :userId")
    fun getUser(userId: Int): Flow<UserEntity>

    @Delete
    fun deleteUser(userEntity: UserEntity): Int
}