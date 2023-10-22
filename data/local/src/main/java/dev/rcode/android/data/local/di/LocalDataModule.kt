package dev.rcode.android.data.local.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rcode.android.data.local.datastore.ChatDataStore
import dev.rcode.android.data.local.datastore.ChatDataStoreImpl
import dev.rcode.android.domain.repository.UserPreferenceRepository

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataModule {

    @Binds
    fun bindChatDataStore(
        chatDataStore: ChatDataStoreImpl
    ): ChatDataStore

}