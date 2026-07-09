package com.example.overscratchedcredit.urepo

import com.example.overscratchedcredit.uNetwork.ManualRecoveryRequest
import com.example.overscratchedcredit.uNetwork.RecoveryApi
import com.example.overscratchedcredit.ussd.UssdRequest
import com.example.overscratchedcredit.ussd.UssdResponse

class RecoveryRepository(private val api: RecoveryApi) {

    suspend fun recoverPin(serial: String, amount: String, partialPin: String, phone: String): Result<String> {
        return try {
            val response = api.recoverManual(
                ManualRecoveryRequest(serial, amount, partialPin, phone)
            )
            Result.success(response.pin)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun confirmUssdTopup(amount: String, partialPin: String, serial: String): Result<UssdResponse> {
        return try {
            val response = api.recoverUssd(UssdRequest(amount, partialPin, serial))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}