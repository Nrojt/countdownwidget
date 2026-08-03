package com.nrojt.countdownwidget.di

import androidx.room.Room
import com.nrojt.countdownwidget.data.CountdownDatabase
import com.nrojt.countdownwidget.data.CountdownEventDao
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Room-provided dependencies that cannot be auto-wired by Koin annotations
 * (abstract class / interface instantiated by the Room builder).
 */
val roomModule: Module = module {

    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            CountdownDatabase::class.java,
            "countdown_database",
        ).build()
    }

    // DAO
    single<CountdownEventDao> { get<CountdownDatabase>().countdownEventDao() }
}
