package dev.rcode.android.data.main.repository

import dev.rcode.android.core.base.error.DataBaseException
import dev.rcode.android.data.local.datastore.ChatDataStore
import dev.rcode.android.data.local.db.ChatDatabase
import dev.rcode.android.core.base.error.NoDataException
import dev.rcode.android.domain.model.User
import dev.rcode.android.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(
    private val dataStore: ChatDataStore,
    private val db: ChatDatabase
): UserPreferenceRepository {
    override suspend fun getThisUser(): Flow<Result<User>> {
        //return dataStore.getUserData()
        return db.userDao().getUser(1).map { userEntity ->
            if(userEntity != null)
                Result.success(userEntity.toModel())
            else
                Result.failure(NoDataException())
        }
    }

    override suspend fun saveThisUser(user: User): Flow<Result<Boolean>> {
        // return flow {dataStore.storeUserData(user) }
        var userEntityId = db.userDao().createUser(dev.rcode.android.data.local.db.entities.UserEntity.fromModel(user))
        return flow {
            emit(if(userEntityId != -1L) Result.success(true) else Result.failure(DataBaseException()))
        }
    }

    override suspend fun deleteThisUser(user: User): Flow<Result<Boolean>> {
        var deleteStatus = db.userDao().deleteUser(
            userEntity = dev.rcode.android.data.local.db.entities.UserEntity.fromModel(user)
        )
        return flow {
            try {
                emit(Result.success(true))
            } catch (e: Exception){
                emit(Result.failure(NoDataException()))
            }
        }
    }

}