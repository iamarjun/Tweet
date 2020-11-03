package com.arjun.tweet.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TweetsResponse(
    @Json(name = "data")
    val `data`: List<Data> = listOf(),
    @Json(name = "success")
    val success: Boolean = false
)