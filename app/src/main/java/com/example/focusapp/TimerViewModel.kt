package com.example.focusapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class FocusMode(val durationMinutes: Int) {
    FOCUS(25),
    SHORT_BREAK(5),
    LONG_BREAK(15);

    val durationSeconds: Long = durationMinutes * 60L
}

enum class TimerStatus {
    IDLE,
    RUNNING,
    PAUSED
}

data class TimerUiState(
    val remainingTimeSeconds: Long,
    val totalTimeSeconds: Long,
    val timerStatus: TimerStatus,
    val currentMode: FocusMode
)

class TimerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        TimerUiState(
            remainingTimeSeconds = FocusMode.FOCUS.durationSeconds,
            totalTimeSeconds = FocusMode.FOCUS.durationSeconds,
            timerStatus = TimerStatus.IDLE,
            currentMode = FocusMode.FOCUS
        )
    )
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun setMode(mode: FocusMode) {
        stopTimer()
        _uiState.update {
            it.copy(
                currentMode = mode,
                remainingTimeSeconds = mode.durationSeconds,
                totalTimeSeconds = mode.durationSeconds,
                timerStatus = TimerStatus.IDLE
            )
        }
    }

    fun startTimer() {
        if (_uiState.value.timerStatus == TimerStatus.RUNNING) return

        _uiState.update { it.copy(timerStatus = TimerStatus.RUNNING) }
        timerJob = viewModelScope.launch {
            while (_uiState.value.remainingTimeSeconds > 0) {
                delay(1000L)
                _uiState.update {
                    it.copy(remainingTimeSeconds = it.remainingTimeSeconds - 1)
                }
            }
            onTimerFinished()
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _uiState.update { it.copy(timerStatus = TimerStatus.PAUSED) }
    }

    fun resetTimer() {
        stopTimer()
        _uiState.update {
            it.copy(
                remainingTimeSeconds = it.currentMode.durationSeconds,
                timerStatus = TimerStatus.IDLE
            )
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun onTimerFinished() {
        _uiState.update { it.copy(timerStatus = TimerStatus.IDLE, remainingTimeSeconds = 0) }
        // Completion alerts will be handled in the UI observing this state
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}
