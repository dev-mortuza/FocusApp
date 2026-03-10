package com.example.focusapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.focusapp.di.ServiceLocator
import com.example.focusapp.domain.entity.FocusMode
import com.example.focusapp.domain.entity.TimerState
import com.example.focusapp.domain.usecase.GetTimerStateUseCase
import com.example.focusapp.domain.usecase.PauseTimerUseCase
import com.example.focusapp.domain.usecase.ResetTimerUseCase
import com.example.focusapp.domain.usecase.SetModeUseCase
import com.example.focusapp.domain.usecase.StartTimerUseCase
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel : ViewModel() {

    // Use Cases
    private val setModeUseCase: SetModeUseCase = ServiceLocator.provideSetModeUseCase()
    private val startTimerUseCase: StartTimerUseCase = ServiceLocator.provideStartTimerUseCase()
    private val pauseTimerUseCase: PauseTimerUseCase = ServiceLocator.providePauseTimerUseCase()
    private val resetTimerUseCase: ResetTimerUseCase = ServiceLocator.provideResetTimerUseCase()
    private val getTimerStateUseCase: GetTimerStateUseCase = ServiceLocator.provideGetTimerStateUseCase()

    // UI State - Reactive state holder that observers can subscribe to
    val uiState: StateFlow<TimerState> = getTimerStateUseCase()

    // Timer Control Methods
    fun setMode(mode: FocusMode) {
        setModeUseCase(mode)
    }

    fun startTimer() {
        startTimerUseCase()
    }

    fun pauseTimer() {
        pauseTimerUseCase()
    }

    fun resetTimer() {
        resetTimerUseCase()
    }

    // Lifecycle Management
    override fun onCleared() {
        super.onCleared()
        ServiceLocator.provideTimerRepository().cleanup()
    }
}

