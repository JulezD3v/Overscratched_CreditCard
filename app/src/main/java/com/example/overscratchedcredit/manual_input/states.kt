package com.example.overscratchedcredit.manual_input

sealed class RecoveryUiState {
    object Idle : RecoveryUiState()
    object Loading : RecoveryUiState()
    data class Success(val pin: String) : RecoveryUiState()
    data class Error(val message: String) : RecoveryUiState()
}

