package com.example.overscratchedcredit.ussd.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.overscratchedcredit.commonUi.theme.OverScratchedCreditTheme
import com.example.overscratchedcredit.commonUi.widgets.UssdMenuItem
import com.example.overscratchedcredit.commonUi.widgets.UssdStyleDialog
import kotlin.random.Random

private val mockValidVouchers = mapOf(
    "1234567890123" to "100",
    "9876543210987" to "50"
)

@Composable
fun UssdDemoScreen(
    onDismiss: () -> Unit = {},
    onCheckStatusClick: () -> Unit = {},
    onHelpClick: () -> Unit = {}
) {
    var showUssdMenu by rememberSaveable { mutableStateOf(true) }
    var currentPage by rememberSaveable { mutableStateOf("home") }

    var enteredSerial by rememberSaveable { mutableStateOf("") }
    var selectedAmount by rememberSaveable { mutableStateOf("") }
    var isRandomSerial by rememberSaveable { mutableStateOf(false) }

    fun generateRandomSerial(): String =
        (1..13).map{ Random.nextInt(0, 10).toString() }.joinToString("")

    // Random serials always succeed. Manual entries checked against mock DB.
    fun isValidRecovery(serial: String, amount: String): Boolean {
        if (isRandomSerial) return true
        return mockValidVouchers[serial] == amount
    }

    if (showUssdMenu) {
        when (currentPage) {

            "home" -> {
                UssdStyleDialog(
                    message = "Voucher Recovery\n\nSelect an option:",
                    menuItems = listOf(
                        UssdMenuItem(1, "Recover voucher") { currentPage = "recover" },
                        UssdMenuItem(2, "Check recovery status") {
                            showUssdMenu = false
                            onCheckStatusClick()
                        },
                        UssdMenuItem(3, "Help") {
                            showUssdMenu = false
                            onHelpClick()
                        }
                    ),
                    onDismiss = { showUssdMenu = false; onDismiss() }
                )
            }

            "recover" -> {
                UssdStyleDialog(
                    message = "Recover Voucher\n\nEnter voucher details:",
                    menuItems = listOf(
                        UssdMenuItem(1, "Enter serial number") { currentPage = "serial" },
                        UssdMenuItem(2, "Send random serial") {
                            enteredSerial = generateRandomSerial()
                            isRandomSerial = true
                            currentPage = "amount"
                        },
                        UssdMenuItem(0, "Back") { currentPage = "home" }
                    ),
                    onDismiss = { showUssdMenu = false; onDismiss() }
                )
            }

            "serial" -> {
                UssdStyleDialog(
                    message = "Enter voucher serial number:",
                    onFreeTextSubmit = { serial ->
                        enteredSerial = serial
                        isRandomSerial = false
                        currentPage = "amount"
                    },
                    inputValidation = { it.length == 13 },
                    inputErrorMessage = "Serial must be exactly 13 digits.",
                    inputPlaceholder = "13-digit serial number",
                    onDismiss = { showUssdMenu = false; onDismiss() }
                )
            }

            "amount" -> {
                UssdStyleDialog(
                    message = "Serial: $enteredSerial\n\nSelect amount:",
                    menuItems = listOf(
                        UssdMenuItem(1, "KES 20") { selectedAmount = "20"; currentPage = "partial_pin" },
                        UssdMenuItem(2, "KES 50") { selectedAmount = "50"; currentPage = "partial_pin" },
                        UssdMenuItem(3, "KES 100") { selectedAmount = "100"; currentPage = "partial_pin" },
                        UssdMenuItem(4, "KES 200") { selectedAmount = "200"; currentPage = "partial_pin" },
                        UssdMenuItem(5, "KES 500") { selectedAmount = "500"; currentPage = "partial_pin" }
                    ),
                    onDismiss = { showUssdMenu = false; onDismiss() }
                )
            }

            // Replaces phone verification — user enters whatever PIN digits are legible
            "partial_pin" -> {
                UssdStyleDialog(
                    message = "Enter the visible PIN digits\nfrom your scratched voucher:",
                    onFreeTextSubmit = { _ ->
                        // partial PIN isn't used to look up the voucher — serial + amount already identify it.
                        // It's still collected here to match the real recovery flow's data entry step.
                        currentPage = if (isValidRecovery(enteredSerial, selectedAmount)) {
                            "result_valid"
                        } else {
                            "result_invalid"
                        }
                    },
                    inputValidation = { it.isNotEmpty() && it.length <= 16 },
                    inputErrorMessage = "Enter up to 16 visible digits.",
                    inputPlaceholder = "Visible PIN digits",
                    onDismiss = { showUssdMenu = false; onDismiss() }
                )
            }

            "result_valid" -> {
                UssdStyleDialog(
                    message = "Status: VALID\n\n" +
                            "KES $selectedAmount airtime has been added to your account.",
                    onDismiss = { showUssdMenu = false; onDismiss() }
                )
            }

            "result_invalid" -> {
                UssdStyleDialog(
                    message = "Recovery failed.\n\n" +
                            "Serial and amount do not match our records.\n" +
                            "Please check the details and try again, or call 100 for help.",
                    menuItems = listOf(
                        UssdMenuItem(0, "Back to menu") {
                            enteredSerial = ""
                            selectedAmount = ""
                            currentPage = "home"
                        }
                    ),
                    onDismiss = { showUssdMenu = false; onDismiss() }
                )
            }
        }
    } else {
        onDismiss()
    }
}

@Preview
@Composable
private fun UssdDemoScreenPreview() {
    OverScratchedCreditTheme {
        UssdDemoScreen()
    }
}