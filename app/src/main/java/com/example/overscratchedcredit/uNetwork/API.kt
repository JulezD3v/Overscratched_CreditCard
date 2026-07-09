package com.example.overscratchedcredit.uNetwork

import com.example.overscratchedcredit.ussd.UssdRequest
import com.example.overscratchedcredit.ussd.UssdResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RecoveryApi {

    @POST("api/v1/recover")
    suspend fun recoverManual(@Body request: ManualRecoveryRequest): ManualRecoveryResponse

    @POST("api/v1/recover/ussd")
    suspend fun recoverUssd(@Body request: UssdRequest): UssdResponse
}

data class ManualRecoveryRequest(
    val serial: String,
    val amount: String,
    val partialPin: String,
    val phone: String
)

data class ManualRecoveryResponse(
    val pin: String
)