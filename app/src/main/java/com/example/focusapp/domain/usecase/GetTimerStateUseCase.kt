package com.example.focusapp.domain.usecase

import com.example.focusapp.domain.entity.TimerState
import com.example.focusapp.domain.repository.TimerRepository
import kotlinx.coroutines.flow.StateFlow

class GetTimerStateUseCase(private val timerRepository: TimerRepository) {
    operator fun invoke(): StateFlow<TimerState> {
        return timerRepository.timerState
    }
}

