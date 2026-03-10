package com.example.focusapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.focusapp.domain.entity.TimerStatus

@Composable
fun TimerControls(
    status: TimerStatus,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Reset Button
        OutlinedIconButton(
            onClick = onReset,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Reset Timer",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(32.dp))

        // Play/Pause Button
        LargeFloatingActionButton(
            onClick = {
                if (status == TimerStatus.RUNNING) onPause() else onStart()
            },
            containerColor = if (status == TimerStatus.RUNNING) Color.Red else MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.size(80.dp)
        ) {
            Icon(
                imageVector = if (status == TimerStatus.RUNNING) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (status == TimerStatus.RUNNING) "Pause" else "Start",
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.width(88.dp))
    }
}

