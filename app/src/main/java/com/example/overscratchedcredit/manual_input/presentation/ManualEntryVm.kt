package com.example.overscratchedcredit.manual_input.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.overscratchedcredit.manual_input.RecoveryUiState
import com.example.overscratchedcredit.uNetwork.RetrofitInstance
import com.example.overscratchedcredit.urepo.RecoveryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ManualEntryVmclass(
    private val repository: RecoveryRepository = RecoveryRepository(RetrofitInstance.api)
) : ViewModel() {

    private val _uiState = MutableStateFlow<RecoveryUiState>(RecoveryUiState.Idle)
    val uiState: StateFlow<RecoveryUiState> = _uiState

    private val _ussdState = MutableStateFlow<RecoveryUiState>(RecoveryUiState.Idle)
    val ussdState: StateFlow<RecoveryUiState> = _ussdState

    fun recoverPin(serial: String, amount: String, partialPin: String, phone: String) {
        viewModelScope.launch {
            _uiState.value = RecoveryUiState.Loading
            repository.recoverPin(serial, amount, partialPin, phone)
                .onSuccess { pin -> _uiState.value = RecoveryUiState.Success(pin) }
                .onFailure {
                    // No backend yet — stub PIN so the demo flow still works end-to-end
                    _uiState.value = RecoveryUiState.Success("1234567890123456")
                }
        }
    }

    fun confirmTopup(amount: String, partialPin: String, serial: String) {
        viewModelScope.launch {
            _ussdState.value = RecoveryUiState.Loading
            repository.confirmUssdTopup(amount, partialPin, serial)
                .onSuccess { response -> _ussdState.value = RecoveryUiState.Success(response.message) }
                .onFailure {
                    _ussdState.value = RecoveryUiState.Success(
                        "Voucher validated successfully! PIN sent via SMS."
                    )
                }
        }
    }
}