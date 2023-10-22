package dev.rcode.android.domain.model

import com.google.gson.Gson
import org.json.JSONObject


data class User(
    var userId: Long,
    var name: String,
    var email: String,
) {
    fun isUserLoggedIn() = userId > 0

    fun toJson(): String {
        return Gson().toJson(this)
    }

}

fun String.toUserModel(): User {
    val userJson = JSONObject(this)
    return User(userJson["userId"].toString().toLong(), userJson["name"].toString(), userJson["email"].toString())
}