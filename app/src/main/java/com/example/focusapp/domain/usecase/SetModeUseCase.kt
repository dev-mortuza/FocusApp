package com.example.focusapp.domain.usecase

import com.example.focusapp.domain.entity.FocusMode
import com.example.focusapp.domain.repository.TimerRepository

class SetModeUseCase(private val timerRepository: TimerRepository) {
    operator fun invoke(mode: FocusMode) {
        timerRepository.setMode(mode)
    }
}

