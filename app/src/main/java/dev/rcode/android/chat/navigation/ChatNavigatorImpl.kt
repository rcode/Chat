package dev.rcode.android.chat.navigation

import androidx.navigation.NavController
import dev.rcode.android.chat.R
import dev.rcode.android.core.ui.navigation.ChatNavigator
import dev.rcode.android.feature.chat.ChatListFragmentDirections
import javax.inject.Inject
import javax.inject.Singleton

class ChatNavigatorImpl @Inject constructor(private val navController: NavController): ChatNavigator {

    override fun goToChatScreen(chatGroup: String) {
        val action = ChatListFragmentDirections.actionChatListFragmentToChatFragment(chatGroup)
        navController.navigate(action)
    }

    override fun goToRegistrationScreen() {
        navController.navigate(R.id.action_chatListFragment_to_registrationFragment)
    }
}