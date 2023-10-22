package dev.rcode.android.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import org.jetbrains.annotations.NotNull

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    var userId: Long,
    var name: String,
    var email: String,
) {
    fun isUserLoggedIn() = userId > 0

    companion object {
        fun fromModel(user: dev.rcode.android.domain.model.User): UserEntity {
            return UserEntity(user.userId, user.name, user.email)
        }
    }

    fun toJson(): String {
        return  Gson().toJson(this)
    }

    fun toModel(): dev.rcode.android.domain.model.User {
        return dev.rcode.android.domain.model.User(this.userId, this.name, this.email)
    }

}