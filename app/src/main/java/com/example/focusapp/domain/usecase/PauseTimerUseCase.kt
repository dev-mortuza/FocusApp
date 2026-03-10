package com.example.focusapp.domain.usecase

import com.example.focusapp.domain.repository.TimerRepository

class PauseTimerUseCase(private val timerRepository: TimerRepository) {
    operator fun invoke() {
        timerRepository.pauseTimer()
    }
}

