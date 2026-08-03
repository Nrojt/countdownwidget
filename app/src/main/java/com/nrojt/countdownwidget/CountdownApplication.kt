package com.nrojt.countdownwidget

import android.app.Application
import com.nrojt.countdownwidget.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CountdownApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CountdownApplication)
            modules(appModule)
        }
    }
}
