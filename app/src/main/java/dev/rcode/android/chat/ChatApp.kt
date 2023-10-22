package dev.rcode.android.chat

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChatApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }

    fun initializeApp() {
        //FirebaseApp.initializeApp()
    }
}