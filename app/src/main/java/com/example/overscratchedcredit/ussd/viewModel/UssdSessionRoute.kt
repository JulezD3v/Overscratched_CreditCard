package com.example.overscratchedcredit.ussd.viewModel

import com.example.overscratchedcredit.ussd.presentation.UssdSessionVmclass
import com.example.overscratchedcredit.ussd.util.UssdSessionStep
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.overscratchedcredit.commonUi.widgets.UssdMenuItem
import com.example.overscratchedcredit.commonUi.widgets.UssdStyleDialog


@Composable
fun UssdSessionRoute(
    viewModel: UssdSessionVmclass = viewModel(),
    onDismiss: () -> Unit
) {
    val step by viewModel.step.collectAsState()

    when (val current = step) {

        // Image 1 — free-text serial entry
        is UssdSessionStep.EnterSerial -> UssdStyleDialog(
            message = "Enter voucher serial number:",
            onFreeTextSubmit = { serial -> viewModel.submitSerial(serial) },
            inputValidation = { it.length == 13 },
            inputErrorMessage = "Serial must be exactly 13 digits.",
            inputPlaceholder = "13-digit serial number",
            onDismiss = onDismiss
        )

        // Image 2 — menu selection
        is UssdSessionStep.SelectAmount -> UssdStyleDialog(
            message = "Select amount:",
            menuItems = listOf(
                UssdMenuItem(1, "KES 20") { viewModel.selectAmount(current.serial, "20") },
                UssdMenuItem(2, "KES 50") { viewModel.selectAmount(current.serial, "50") },
                UssdMenuItem(3, "KES 100") { viewModel.selectAmount(current.serial, "100") },
                UssdMenuItem(4, "KES 200") { viewModel.selectAmount(current.serial, "200") },
                UssdMenuItem(5, "KES 500") { viewModel.selectAmount(current.serial, "500") }
            ),
            onDismiss = onDismiss
        )

        // Image 3 — Yes/No confirm
        is UssdSessionStep.ConfirmRecovery -> UssdStyleDialog(
            message = "Voucher found!\nAmount: KES ${current.amount}\nSerial: ${current.serial}\n\nRecover PIN?",
            menuItems = listOf(
                UssdMenuItem(1, "Yes") { viewModel.confirmYes(current.serial, current.amount) },
                UssdMenuItem(2, "No, cancel") { viewModel.confirmNo() }
            ),
            onDismiss = onDismiss
        )

        // Image 4 — free-text phone entry
        is UssdSessionStep.VerifyPhone -> UssdStyleDialog(
            message = "Verify your number:",
            onFreeTextSubmit = { phone -> viewModel.submitPhone(current.serial, current.amount, phone) },
            inputValidation = { it.length in 9..10 },
            inputErrorMessage = "Enter a valid Safaricom number.",
            inputPlaceholder = "07XXXXXXXX",
            onDismiss = onDismiss
        )

        // Image 5 — final result, no input, just OK
        is UssdSessionStep.ShowResult -> UssdStyleDialog(
            message = "Your PIN is:\n${current.pin}\n\nDial *141*${current.pin}# to top up.\nSMS sent to ${current.phone}",
            onDismiss = onDismiss
        )
    }
}