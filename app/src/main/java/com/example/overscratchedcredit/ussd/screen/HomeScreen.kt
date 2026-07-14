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

@Composable
fun UssdDemoScreen(
    onDismiss: () -> Unit = {},
    onCheckStatusClick: () -> Unit = {},
    onHelpClick: () -> Unit = {}
) {
    var showUssdMenu by rememberSaveable { mutableStateOf(true) }
    var currentPage by rememberSaveable { mutableStateOf("home") }
    var lastGeneratedSerial by rememberSaveable { mutableStateOf("") }

    // Helper to generate a random 13-digit serial number
    fun generateRandomSerial(): String {
        return (1..13).map { Random.nextInt(0, 10).toString() }.joinToString("")
    }

    if (showUssdMenu) {
        when (currentPage) {
            "home" -> {
                UssdStyleDialog(
                    message = "Voucher Recovery\n\nSelect an option:",
                    menuItems = listOf(
                        UssdMenuItem(
                            number = 1,
                            label = "Recover voucher",
                            onClick = { currentPage = "recover" }
                        ),
                        UssdMenuItem(
                            number = 2,
                            label = "Check recovery status",
                            onClick = { 
                                showUssdMenu = false
                                onCheckStatusClick() 
                            }
                        ),
                        UssdMenuItem(
                            number = 3,
                            label = "Help",
                            onClick = { 
                                showUssdMenu = false
                                onHelpClick() 
                            }
                        )
                    ),
                    onDismiss = { 
                        showUssdMenu = false
                        onDismiss()
                    }
                )
            }

            "recover" -> {
                UssdStyleDialog(
                    message = "Recover Voucher\n\nEnter voucher details:",
                    menuItems = listOf(
                        UssdMenuItem(
                            number = 1,
                            label = "Enter serial number",
                            onClick = { currentPage = "serial" }
                        ),
                        UssdMenuItem(
                            number = 2,
                            label = "Send random serial",
                            onClick = { 
                                lastGeneratedSerial = generateRandomSerial()
                                currentPage = "confirm_random" 
                            }
                        ),
                        UssdMenuItem(
                            number = 0,
                            label = "Back",
                            onClick = { currentPage = "home" }
                        )
                    ),
                    onDismiss = { 
                        showUssdMenu = false
                        onDismiss()
                    }
                )
            }

            "confirm_random" -> {
                UssdStyleDialog(
                    message = "Voucher Found!\nSerial: $lastGeneratedSerial\nAmount: KES 100\n\nRecover PIN?",
                    menuItems = listOf(
                        UssdMenuItem(1, "Yes") { currentPage = "result_mock" },
                        UssdMenuItem(2, "No, cancel") { currentPage = "home" }
                    ),
                    onDismiss = { 
                        showUssdMenu = false
                        onDismiss()
                    }
                )
            }

            "result_mock" -> {
                UssdStyleDialog(
                    message = "Your PIN is:\n7482 9103 4721 0034\n\nDial *141*PIN# to top up.\nSMS sent to your number.",
                    onDismiss = { 
                        showUssdMenu = false
                        onDismiss()
                    }
                )
            }

            "serial" -> {
                UssdStyleDialog(
                    message = "Enter your 13-digit voucher serial number.",
                    menuItems = listOf(
                        UssdMenuItem(
                            number = 0,
                            label = "Back",
                            onClick = { currentPage = "recover" }
                        )
                    ),
                    onDismiss = { 
                        showUssdMenu = false
                        onDismiss()
                    }
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
