package com.nrojt.countdownwidget.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

/**
 * Koin annotation module that auto-discovers all annotated components
 * (e.g. @Single, @KoinViewModel) in the project.
 *
 * Loaded automatically via `@KoinApplication` on
 * [com.nrojt.countdownwidget.CountdownApplication].
 */
@Module
@ComponentScan("com.nrojt.countdownwidget")
class CountdownModule
