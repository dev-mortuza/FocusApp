package com.example.focusapp.di

import com.example.focusapp.data.repository.TimerRepositoryImpl
import com.example.focusapp.domain.repository.TimerRepository
import com.example.focusapp.domain.usecase.GetTimerStateUseCase
import com.example.focusapp.domain.usecase.PauseTimerUseCase
import com.example.focusapp.domain.usecase.ResetTimerUseCase
import com.example.focusapp.domain.usecase.SetModeUseCase
import com.example.focusapp.domain.usecase.StartTimerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object ServiceLocator {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val timerRepository: TimerRepository by lazy {
        TimerRepositoryImpl(applicationScope)
    }

    fun provideSetModeUseCase(): SetModeUseCase {
        return SetModeUseCase(timerRepository)
    }

    fun provideStartTimerUseCase(): StartTimerUseCase {
        return StartTimerUseCase(timerRepository)
    }

    fun providePauseTimerUseCase(): PauseTimerUseCase {
        return PauseTimerUseCase(timerRepository)
    }

    fun provideResetTimerUseCase(): ResetTimerUseCase {
        return ResetTimerUseCase(timerRepository)
    }

    fun provideGetTimerStateUseCase(): GetTimerStateUseCase {
        return GetTimerStateUseCase(timerRepository)
    }

    fun provideTimerRepository(): TimerRepository {
        return timerRepository
    }
}

