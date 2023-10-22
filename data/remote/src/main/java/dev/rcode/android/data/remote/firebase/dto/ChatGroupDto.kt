package dev.rcode.android.data.remote.firebase.dto

import com.google.gson.annotations.SerializedName

data class ChatGroupDto(
    @SerializedName("chat_group_id")
    var chat_group_id: Long? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("created_at")
    var created_at: Long? = 0
)
