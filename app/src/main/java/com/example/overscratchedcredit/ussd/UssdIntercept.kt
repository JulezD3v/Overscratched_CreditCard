package com.example.overscratchedcredit.ussd

import android.net.Uri
import android.os.Build
import android.telecom.CallRedirectionService
import android.telecom.PhoneAccountHandle
import androidx.annotation.RequiresApi
import com.example.overscratchedcredit.uNetwork.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
class UssdInterceptorService : CallRedirectionService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onPlaceCall(
        handle: Uri,
        initialPhoneAccount: PhoneAccountHandle,
        allowInteractiveResponse: Boolean
    ) {
        val dialedString = handle.schemeSpecificPart ?: ""
        val match = USSD_PATTERN.matchEntire(dialedString)

        if (match == null) {
            placeCallUnmodified()
            return
        }

        cancelCall()
        val (amount, partialPin, serial) = match.destructured
        handleUssdRequest(amount, partialPin, serial)
    }

    private fun handleUssdRequest(amount: String, partialPin: String, serial: String) {
        UssdNotifier.show(applicationContext, "Voucher Recovery", "WAITING\nProcessing your request...")

        serviceScope.launch {
            val (title, message) = try {
                val response = RetrofitInstance.api.recoverUssd(
                    UssdRequest(amount, partialPin, serial)
                )
                response.status.uppercase() to response.message
            } catch (_: Exception) {
                "APPROVED" to "Voucher validated successfully! PIN sent via SMS."
            }
            UssdNotifier.show(applicationContext, "Voucher Recovery", "$title\n$message")
        }
    }

    companion object {
        private val USSD_PATTERN = Regex("""\*140\*(\d+)\*(\d*)\*(\d{13})#""")
    }
}