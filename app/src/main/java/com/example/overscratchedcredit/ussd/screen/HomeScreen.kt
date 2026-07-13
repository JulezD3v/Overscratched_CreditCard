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

@Composable
fun UssdDemoScreen(
    onCheckStatusClick: () -> Unit = {},
    onHelpClick: () -> Unit = {}
) {
    var showUssdMenu by rememberSaveable { mutableStateOf(true) }
    var currentPage by rememberSaveable { mutableStateOf("home") }

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
                    onDismiss = { showUssdMenu = false }
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
                            number = 0,
                            label = "Back",
                            onClick = { currentPage = "home" }
                        )
                    ),
                    onDismiss = { showUssdMenu = false }
                )
            }

            "status" -> {
                UssdStyleDialog(
                    message = "Recovery Status\n\nNo recovery requests found.",
                    menuItems = listOf(
                        UssdMenuItem(
                            number = 0,
                            label = "Back",
                            onClick = { currentPage = "home" }
                        )
                    ),
                    onDismiss = { showUssdMenu = false }
                )
            }

            "help" -> {
                UssdStyleDialog(
                    message = "Help\n\nUse your voucher serial number to recover a damaged PIN.",
                    menuItems = listOf(
                        UssdMenuItem(
                            number = 0,
                            label = "Back",
                            onClick = { currentPage = "home" }
                        )
                    ),
                    onDismiss = { showUssdMenu = false }
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
                    onDismiss = { showUssdMenu = false }
                )
            }
        }
    }
}

@Preview
@Composable
private fun UssdDemoScreenPreview() {
    OverScratchedCreditTheme {
        UssdDemoScreen()
    }
}
