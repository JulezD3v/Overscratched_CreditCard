package com.example.overscratchedcredit.ussd.util

data class UssdRequest(
    val amount: String,
    val partialPin: String,
    val serial: String
)

data class UssdResponse(
    val status: String,   // "approved"  "waiting"  "not_approved"
    val message: String   // "Voucher validated successfully!"
)