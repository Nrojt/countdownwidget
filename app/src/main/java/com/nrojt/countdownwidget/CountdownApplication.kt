package com.nrojt.countdownwidget

import android.app.Application
import com.nrojt.countdownwidget.di.CountdownModule
import com.nrojt.countdownwidget.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinApplication
import org.koin.core.logger.Level
import org.koin.plugin.module.dsl.startKoin

/**
 * Application entry point. Configures Koin dependency injection by merging
 * the auto-generated [CountdownModule] with the manually provided [roomModule].
 */
@KoinApplication(modules = [CountdownModule::class])
class CountdownApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin<CountdownApplication> {
            androidLogger(Level.ERROR)
            androidContext(this@CountdownApplication)
            modules(roomModule)
        }
    }
}
