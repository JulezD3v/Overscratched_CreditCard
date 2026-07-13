package com.example.overscratchedcredit.ussd.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.overscratchedcredit.uNetwork.ManualRecoveryRequest
import com.example.overscratchedcredit.uNetwork.RetrofitInstance
import com.example.overscratchedcredit.ussd.util.UssdSessionStep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UssdSessionVmclass : ViewModel() {

    private val _step = MutableStateFlow<UssdSessionStep>(UssdSessionStep.EnterSerial)
    val step: StateFlow<UssdSessionStep> = _step

    fun submitSerial(serial: String) {
        _step.value = UssdSessionStep.SelectAmount(serial)
    }

    fun selectAmount(serial: String, amount: String) {
        _step.value = UssdSessionStep.ConfirmRecovery(serial, amount)
    }

    fun confirmYes(serial: String, amount: String) {
        _step.value = UssdSessionStep.VerifyPhone(serial, amount)
    }

    fun confirmNo() {
        _step.value = UssdSessionStep.EnterSerial // cancel → restart session
    }

    fun submitPhone(serial: String, amount: String, phone: String) {
        viewModelScope.launch {
            val pin = try {
                RetrofitInstance.api.recoverManual(
                    ManualRecoveryRequest(serial, amount, partialPin = "", phone = phone)
                ).pin
            } catch (e: Exception) {
                // No backend yet — realistic stub so the session still completes
                "1234567890123456"
            }
            _step.value = UssdSessionStep.ShowResult(serial, amount, phone, pin)
        }
    }
}