package dev.rcode.android.domain.model

data class Message(
    val messageId: Long,
    val chatGroupId: Long,
    val text: String,
    val timestamp: Long,
    val userId: Long,
    val isRead: Boolean,
    val isPosted: Boolean = false
)
