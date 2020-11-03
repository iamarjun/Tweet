package com.arjun.tweet.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "favoriteCount")
    val favoriteCount: Int = 0,
    @Json(name = "handle")
    val handle: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "profileImageUrl")
    val profileImageUrl: String = "",
    @Json(name = "retweetCount")
    val retweetCount: Int = 0,
    @Json(name = "text")
    val text: String = ""
) {
    fun contains(charSequence: CharSequence): Boolean =
        (handle.contains(charSequence, ignoreCase = true) ||
                name.contains(charSequence, ignoreCase = true) ||
                text.contains(charSequence, ignoreCase = true))
}