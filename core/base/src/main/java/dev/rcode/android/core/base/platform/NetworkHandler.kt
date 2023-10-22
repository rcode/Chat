package dev.rcode.android.core.base.platform

import android.content.Context
import dev.rcode.android.core.base.extension.isConnected
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler @Inject constructor(private val context: Context) {
    val isConnected get() = context.isConnected
}