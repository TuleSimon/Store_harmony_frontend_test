package com.simon.storeharmonytest.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class StoreHarmonyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        planTimberLogging()
    }

    //TImber used for logging during debugging, better option than built in logger
    private fun planTimberLogging(){
        Timber.plant(Timber.DebugTree())
    }
}