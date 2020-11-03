package com.arjun.tweet.services

import com.arjun.tweet.models.TweetsResponse
import retrofit2.http.GET


interface TweetRestApi {

    @GET("tweets")
    suspend fun getTweets(): TweetsResponse
}