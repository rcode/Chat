package dev.rcode.android.chat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dev.rcode.android.chat.navigation.ChatNavigatorImpl
import dev.rcode.android.core.ui.navigation.ChatNavigator

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    @Binds
    abstract fun bindNavigator(chatNavigatorImpl: ChatNavigatorImpl): ChatNavigator
}