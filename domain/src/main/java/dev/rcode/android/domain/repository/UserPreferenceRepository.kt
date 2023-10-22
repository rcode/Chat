package dev.rcode.android.domain.repository

import dev.rcode.android.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

    /**
     * Get the current logged in user
     */
    suspend fun getThisUser(): Flow<Result<User>>

    suspend fun saveThisUser(user: User): Flow<Result<Boolean>>

    suspend fun deleteThisUser(user: User): Flow<Result<Boolean>>
}