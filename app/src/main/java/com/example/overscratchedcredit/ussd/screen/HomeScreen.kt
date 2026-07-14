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

// Mock "voucher DB" — swap for Pearl's real lookup later.
// Only these serial+amount pairs are considered genuinely valid.
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

    // Data collected as the user moves through the flow
    var enteredSerial by rememberSaveable { mutableStateOf("") }
    var selectedAmount by rememberSaveable { mutableStateOf("") }   // raw digits, e.g. "100"
    var isRandomSerial by rememberSaveable { mutableStateOf(false) }

    fun generateRandomSerial(): String =
        (1..13).map { Random.nextInt(0, 10).toString() }.joinToString("")

    // Random-generated serials always succeed (demo reliability).
    // Manually typed serials are checked against the mock DB above,
    // so you can show both the success AND failure path live.
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

            // Image 1 — free-text serial entry, now actually wired
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

            // Image 2 — choose amount
            "amount" -> {
                UssdStyleDialog(
                    message = "Serial: $enteredSerial\n\nSelect amount:",
                    menuItems = listOf(
                        UssdMenuItem(1, "KES 20") { selectedAmount = "20"; currentPage = "verify" },
                        UssdMenuItem(2, "KES 50") { selectedAmount = "50"; currentPage = "verify" },
                        UssdMenuItem(3, "KES 100") { selectedAmount = "100"; currentPage = "verify" },
                        UssdMenuItem(4, "KES 200") { selectedAmount = "200"; currentPage = "verify" },
                        UssdMenuItem(5, "KES 500") { selectedAmount = "500"; currentPage = "verify" }
                    ),
                    onDismiss = { showUssdMenu = false; onDismiss() }
                )
            }

            // Image 4 — verify phone number, then decide valid/invalid
            "verify" -> {
                UssdStyleDialog(
                    message = "Verify your number:",
                    onFreeTextSubmit = { phone ->
                        currentPage = if (isValidRecovery(enteredSerial, selectedAmount)) {
                            "result_valid"
                        } else {
                            "result_invalid"
                        }
                    },
                    inputValidation = { it.length in 9..10 },
                    inputErrorMessage = "Enter a valid Safaricom number.",
                    inputPlaceholder = "07XXXXXXXX",
                    onDismiss = { showUssdMenu = false; onDismiss() }
                )
            }

            // Image 5 — success
            "result_valid" -> {
                UssdStyleDialog(
                    message = "Status: VALID\n\n" +
                            "PIN: 7482 9103 4721 0034\n\n" +
                            "KES $selectedAmount airtime has been added to your account.",
                    onDismiss = { showUssdMenu = false; onDismiss() }
                )
            }

            // Failure path — invalid serial/amount combo
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