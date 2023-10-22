package dev.rcode.android.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ChatGroup(
    var chatGroupId: Long = 0,
    var chatGroupName: String = "",
    var createdOn: Long = 0,
    var isMuted: Boolean = false
) {

    fun getNewObjectJson(): String {
        //return Gson().toJson("{\"timestamp\": $createdOn}")
        return Gson().toJson(this)
    }
}