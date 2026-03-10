package com.example.focusapp.presentation.screen

import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.focusapp.domain.entity.TimerStatus
import com.example.focusapp.presentation.viewmodel.TimerViewModel


@Composable
fun FocusAppMainScreen(viewModel: TimerViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Completion Alert
    LaunchedEffect(uiState.remainingTimeSeconds) {
        if (uiState.remainingTimeSeconds == 0L && uiState.timerStatus == TimerStatus.IDLE) {
            // Play system notification sound
            try {
                val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val r = RingtoneManager.getRingtone(context, notification)
                r.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            snackbarHostState.showSnackbar("Time's up! Great job.")
        }
    }

    FocusAppScreenContent(
        snackbarHostState = snackbarHostState,
        uiState = uiState,
        onModeSelected = { mode -> viewModel.setMode(mode) },
        onStart = { viewModel.startTimer() },
        onPause = { viewModel.pauseTimer() },
        onReset = { viewModel.resetTimer() }
    )
}
