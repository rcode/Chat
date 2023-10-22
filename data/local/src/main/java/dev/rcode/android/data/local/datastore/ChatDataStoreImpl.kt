package dev.rcode.android.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.rcode.android.core.base.error.NoDataException
import dev.rcode.android.domain.model.User
import dev.rcode.android.domain.model.toUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class ChatDataStoreImpl @Inject constructor(private val dataStore: DataStore<Preferences>): ChatDataStore {
    override fun getUserData(): Flow<Result<User>> = dataStore.data.map {
        if(it[PreferencesKeys.USER_DATA] != null)
            Result.success(it[PreferencesKeys.USER_DATA]!!.toUserModel())
        else
            Result.failure(NoDataException())
    }.catch { exception ->
        if(exception is IOException) {
            emit(Result.failure(exception))
        } else {
            emit(Result.failure(exception))
        }
    }

    override suspend fun storeUserData(user: User): Flow<Boolean> {
        dataStore.edit {
            it[PreferencesKeys.USER_DATA] = user.toJson()
        }
        return flow { emit(true) }
    }

    private object PreferencesKeys {
        val USER_DATA = stringPreferencesKey("user_data")
    }
}