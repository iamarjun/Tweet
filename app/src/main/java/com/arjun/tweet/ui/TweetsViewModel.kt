package com.arjun.tweet.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjun.tweet.models.Data
import com.arjun.tweet.services.TweetRestApi
import com.arjun.tweet.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class TweetsViewModel @ViewModelInject constructor(private val restApi: TweetRestApi) :
    ViewModel() {

    private val _tweets by lazy { MutableStateFlow<Resource<List<Data>>>(Resource.Loading) }

    val tweets: StateFlow<Resource<List<Data>>>
        get() = _tweets

    init {

        viewModelScope.launch {
            try {
                val response = restApi.getTweets()
                _tweets.value = Resource.Success(response.data)
            } catch (e: Exception) {
                _tweets.value = Resource.Error(e)
            }
        }
    }

}