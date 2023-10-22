package dev.rcode.android.core.base.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.rcode.android.core.base.platform.NetworkHandler

@InstallIn(SingletonComponent::class)
@Module
object CoreBase {
    @Provides
    fun providesNetworkHandler(@ApplicationContext context: Context): NetworkHandler {
        return NetworkHandler(context)
    }
}