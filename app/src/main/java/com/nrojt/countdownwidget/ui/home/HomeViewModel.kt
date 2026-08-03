package com.nrojt.countdownwidget.ui.home

import androidx.lifecycle.ViewModel
import com.nrojt.countdownwidget.data.repository.CountdownRepository
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val repository: CountdownRepository
) : ViewModel() {
    // TODO: Expose countdown event list, handle deletions
}
