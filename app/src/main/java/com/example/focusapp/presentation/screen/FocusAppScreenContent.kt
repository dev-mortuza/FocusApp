package com.example.focusapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusapp.FocusAppTheme
import com.example.focusapp.domain.entity.FocusMode
import com.example.focusapp.domain.entity.TimerState
import com.example.focusapp.presentation.component.ModeSelector
import com.example.focusapp.presentation.component.TimerControls
import com.example.focusapp.presentation.component.TimerDisplay

/**
 * Screen Layout and Content
 *
 * This file contains the main screen layout for the FocusApp.
 * Merged from legacy ui/FocusAppUI.kt for unified structure.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusAppScreenContent(
    snackbarHostState: SnackbarHostState,
    uiState: TimerState,
    onModeSelected: (FocusMode) -> Unit,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Focus App",
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Mode Switcher
            ModeSelector(
                currentMode = uiState.currentMode,
                onModeSelected = onModeSelected
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Timer Display with Reactive Progress Bar
            TimerDisplay(
                remainingSeconds = uiState.remainingTimeSeconds,
                totalSeconds = uiState.totalTimeSeconds
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Session Controls
            TimerControls(
                status = uiState.timerStatus,
                onStart = onStart,
                onPause = onPause,
                onReset = onReset
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FocusAppScreenContentPreview() {
    FocusAppTheme {
        FocusAppScreenContent(
            snackbarHostState = SnackbarHostState(),
            uiState = TimerState(
                remainingTimeSeconds = 1500,
                totalTimeSeconds = 1500,
                timerStatus = com.example.focusapp.domain.entity.TimerStatus.IDLE,
                currentMode = FocusMode.FOCUS
            ),
            onModeSelected = {},
            onStart = {},
            onPause = {},
            onReset = {}
        )
    }
}

