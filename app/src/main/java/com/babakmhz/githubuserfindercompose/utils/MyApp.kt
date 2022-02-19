package com.babakmhz.githubuserfindercompose.utils

import android.app.Application
import com.babakmhz.githubuserfindercompose.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }


    }

}