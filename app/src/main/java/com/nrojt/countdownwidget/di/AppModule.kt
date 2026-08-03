package com.nrojt.countdownwidget.di

import androidx.room.Room
import com.nrojt.countdownwidget.data.CountdownDatabase
import com.nrojt.countdownwidget.data.repository.CountdownRepository
import com.nrojt.countdownwidget.ui.create.CreateCountdownViewModel
import com.nrojt.countdownwidget.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin
 */

val appModule = module {

    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            CountdownDatabase::class.java,
            "countdown_database"
        ).build()
    }

    // DAO
    single { get<CountdownDatabase>().countdownEventDao() }

    // Repository
    single { CountdownRepository(get()) }

    // ViewModels
    viewModel { HomeViewModel(get()) }
    viewModel { CreateCountdownViewModel(get()) }
}
