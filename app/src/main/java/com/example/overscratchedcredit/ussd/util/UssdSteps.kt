package com.example.overscratchedcredit.ussd.util

sealed class UssdSessionStep {
    object EnterSerial : UssdSessionStep()
    data class SelectAmount(val serial: String) : UssdSessionStep()
    data class ConfirmRecovery(val serial: String, val amount: String) : UssdSessionStep()
    data class VerifyPhone(val serial: String, val amount: String) : UssdSessionStep()
    data class ShowResult(val serial: String, val amount: String, val phone: String, val pin: String) : UssdSessionStep()
}