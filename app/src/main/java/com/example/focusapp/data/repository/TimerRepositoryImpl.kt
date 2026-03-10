package com.example.focusapp.data.repository

import com.example.focusapp.domain.entity.FocusMode
import com.example.focusapp.domain.entity.TimerState
import com.example.focusapp.domain.entity.TimerStatus
import com.example.focusapp.domain.repository.TimerRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class TimerRepositoryImpl(private val coroutineScope: CoroutineScope) : TimerRepository {

    private val _timerState = MutableStateFlow(
        TimerState(
            remainingTimeSeconds = FocusMode.FOCUS.durationSeconds,
            totalTimeSeconds = FocusMode.FOCUS.durationSeconds,
            timerStatus = TimerStatus.IDLE,
            currentMode = FocusMode.FOCUS
        )
    )

    override val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private var timerJob: Job? = null

    override fun setMode(mode: FocusMode) {
        stopTimer()
        _timerState.update {
            it.copy(
                currentMode = mode,
                remainingTimeSeconds = mode.durationSeconds,
                totalTimeSeconds = mode.durationSeconds,
                timerStatus = TimerStatus.IDLE
            )
        }
    }

    override fun startTimer() {
        if (_timerState.value.timerStatus == TimerStatus.RUNNING) return

        _timerState.update { it.copy(timerStatus = TimerStatus.RUNNING) }
        timerJob = coroutineScope.launch(Dispatchers.Default) {
            while (_timerState.value.remainingTimeSeconds > 0) {
                delay(1000L)
                _timerState.update {
                    it.copy(remainingTimeSeconds = it.remainingTimeSeconds - 1)
                }
            }
            onTimerFinished()
        }
    }

    override fun pauseTimer() {
        timerJob?.cancel()
        _timerState.update { it.copy(timerStatus = TimerStatus.PAUSED) }
    }

    override fun resetTimer() {
        stopTimer()
        _timerState.update {
            it.copy(
                remainingTimeSeconds = it.currentMode.durationSeconds,
                timerStatus = TimerStatus.IDLE
            )
        }
    }

    override fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    override fun cleanup() {
        stopTimer()
    }

    private fun onTimerFinished() {
        _timerState.update { it.copy(timerStatus = TimerStatus.IDLE, remainingTimeSeconds = 0) }
    }
}

