package dev.rcode.android.data.main.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rcode.android.data.main.repository.ChatGroupRepositoryImpl
import dev.rcode.android.data.main.repository.MessagesRepositoryImpl
import dev.rcode.android.data.main.repository.UserPreferenceRepositoryImpl
import dev.rcode.android.domain.repository.ChatGroupRepository
import dev.rcode.android.domain.repository.MessagesRepository
import dev.rcode.android.domain.repository.UserPreferenceRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindUserPreferenceRepository(
        userPreferenceRepository: UserPreferenceRepositoryImpl,
    ): UserPreferenceRepository

    @Binds
    fun bindChatGroupRepository(
        chatGroupRepository: ChatGroupRepositoryImpl,
    ): ChatGroupRepository

    @Binds
    fun bindMessagesRepository(
        messagesRepository: MessagesRepositoryImpl,
    ): MessagesRepository

}