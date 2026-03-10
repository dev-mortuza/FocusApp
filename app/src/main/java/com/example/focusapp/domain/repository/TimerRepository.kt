package com.example.focusapp.domain.repository

import com.example.focusapp.domain.entity.FocusMode
import com.example.focusapp.domain.entity.TimerState
import kotlinx.coroutines.flow.StateFlow

interface TimerRepository {
    val timerState: StateFlow<TimerState>

    fun setMode(mode: FocusMode)
    fun startTimer()
    fun pauseTimer()
    fun resetTimer()
    fun stopTimer()
    fun cleanup()
}

