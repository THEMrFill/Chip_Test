package com.themrfill.chiptest

import android.app.Application
import com.themrfill.chiptest.di.networkModule
import com.themrfill.chiptest.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(vmModule, networkModule)
        }
    }
}