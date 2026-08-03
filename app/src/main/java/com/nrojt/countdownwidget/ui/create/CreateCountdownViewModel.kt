package com.nrojt.countdownwidget.ui.create

import androidx.lifecycle.ViewModel
import com.nrojt.countdownwidget.data.repository.CountdownRepository
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class CreateCountdownViewModel(
    private val repository: CountdownRepository
) : ViewModel() {
    // TODO: Handle form state, save countdown event, link widget
}
