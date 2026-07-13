package com.example.overscratchedcredit.ussd

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.overscratchedcredit.commonUi.widgets.UssdMenuItem
import com.example.overscratchedcredit.commonUi.widgets.UssdStyleDialog
import com.example.overscratchedcredit.ussd.viewModel.UssdSessionRoute

@Composable
fun VoucherHomeScreen(
    onCheckStatusClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    var showWelcomeMenu by rememberSaveable { mutableStateOf(true) }
    var showRecoverySession by rememberSaveable { mutableStateOf(false) }

    if (showWelcomeMenu) {
        UssdStyleDialog(
            message = "Welcome to Voucher Recovery\n\nSelect an option:",
            menuItems = listOf(
                UssdMenuItem(1, "Recover voucher") {
                    showWelcomeMenu = false
                    showRecoverySession = true
                },
                UssdMenuItem(2, "Check recovery status") {
                    showWelcomeMenu = false
                    onCheckStatusClick()
                },
                UssdMenuItem(3, "Help") {
                    showWelcomeMenu = false
                    onHelpClick()
                }
            ),
            onDismiss = { showWelcomeMenu = false }
        )
    }

    if (showRecoverySession) {
        UssdSessionRoute(
            onDismiss = { showRecoverySession = false }
        )
    }
}