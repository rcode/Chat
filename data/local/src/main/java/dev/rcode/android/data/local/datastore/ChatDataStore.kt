package dev.rcode.android.data.local.datastore

import dev.rcode.android.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ChatDataStore {

    fun getUserData(): Flow<Result<User>>

    suspend fun storeUserData(user: User): Flow<Boolean>
}