package com.arjun.tweet

import android.app.Application
import timber.log.Timber

class TweetApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}