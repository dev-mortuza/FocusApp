package com.example.focusapp.domain.entity

data class TimerState(
    val remainingTimeSeconds: Long,
    val totalTimeSeconds: Long,
    val timerStatus: TimerStatus,
    val currentMode: FocusMode
)

