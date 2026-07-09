package com.example.overscratchedcredit.manual_input.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.overscratchedcredit.manual_input.RecoveryUiState
import com.example.overscratchedcredit.manual_input.presentation.screen.ManualScreen

@Composable
fun ManualEntryRoute(
    viewModel: ManualEntryVmclass = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToResult: (pin: String, amount: String, serial: String, partialPin: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var lastSubmission by remember { mutableStateOf<Quadruple?>(null) }

    LaunchedEffect(uiState) {
        val state = uiState
        val submission = lastSubmission
        if (state is RecoveryUiState.Success && submission != null) {
            onNavigateToResult(state.pin, submission.amount, submission.serial, submission.partialPin)
        }
    }

    ManualScreen(
        onNavigateBack = onNavigateBack,
        onRecoverClick = { serial, amount, partialPin, phone ->
            lastSubmission = Quadruple(amount, serial, partialPin)
            viewModel.recoverPin(serial, amount, partialPin, phone)
        }
    )
}

private data class Quadruple(val amount: String, val serial: String, val partialPin: String)