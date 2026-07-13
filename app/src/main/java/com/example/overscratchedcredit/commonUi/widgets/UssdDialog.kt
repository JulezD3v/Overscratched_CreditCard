package com.example.overscratchedcredit.commonUi.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

data class UssdMenuItem(
    val number: Int,
    val label: String,
    val onClick: () -> Unit
)

@Composable
fun UssdStyleDialog(
    message: String,
    menuItems: List<UssdMenuItem> = emptyList(),
    onFreeTextSubmit: ((String) -> Unit)? = null,        // NEW — free-text step (serial, phone)
    inputValidation: (String) -> Boolean = { true },       // NEW — e.g. exactly 13 digits
    inputErrorMessage: String = "Invalid entry. Try again.", // NEW — customizable per step
    inputPlaceholder: String = "Enter value",              // NEW — field label per step
    onDismiss: () -> Unit
) {
    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            UssdDialogContent(
                message, menuItems, onFreeTextSubmit,
                inputValidation, inputErrorMessage, inputPlaceholder, onDismiss
            )
        }
    } else {
        Dialog(onDismissRequest = onDismiss) {
            UssdDialogContent(
                message, menuItems, onFreeTextSubmit,
                inputValidation, inputErrorMessage, inputPlaceholder, onDismiss
            )
        }
    }
}

@Composable
private fun UssdDialogContent(
    message: String,
    menuItems: List<UssdMenuItem>,
    onFreeTextSubmit: ((String) -> Unit)?,
    inputValidation: (String) -> Boolean,
    inputErrorMessage: String,
    inputPlaceholder: String,
    onDismiss: () -> Unit
) {
    var typedOption by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

    val showInputField = menuItems.isNotEmpty() || onFreeTextSubmit != null

    fun submitOption() {
        if (onFreeTextSubmit != null) {
            // Free-text mode: whatever was typed IS the data — no menu lookup
            if (inputValidation(typedOption)) {
                onFreeTextSubmit(typedOption)
                typedOption = ""
                errorMessage = null
            } else {
                errorMessage = inputErrorMessage
            }
        } else {
            // Menu mode: typed number must match a menuItem.number
            val selectedItem = menuItems.find { it.number == typedOption.toIntOrNull() }
            if (selectedItem == null) {
                errorMessage = "Invalid option. Try again."
            } else {
                typedOption = ""
                errorMessage = null
                selectedItem.onClick()
            }
        }
    }

    Surface(color = Color(0xFF1B1B1B), shape = MaterialTheme.shapes.small) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = message, color = Color.White, fontFamily = FontFamily.Monospace)

            if (menuItems.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                menuItems.forEach { item ->
                    Text(
                        text = "${item.number}. ${item.label}",
                        color = Color.White,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { item.onClick() }
                            .padding(vertical = 8.dp)
                    )
                }
            }

            if (showInputField) {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = typedOption,
                    onValueChange = { value ->
                        if (value.all { it.isDigit() }) {
                            typedOption = value
                            errorMessage = null
                        }
                    },
                    label = { Text(text = inputPlaceholder, fontFamily = FontFamily.Monospace) },
                    singleLine = true,
                    isError = errorMessage != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { submitOption() }),
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color(0xFFFF6B6B),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancel", color = Color.LightGray, fontFamily = FontFamily.Monospace)
                }

                if (showInputField) {
                    TextButton(onClick = { submitOption() }, enabled = typedOption.isNotBlank()) {
                        Text(text = "Send", color = Color(0xFF4CAF50), fontFamily = FontFamily.Monospace)
                    }
                } else {
                    TextButton(onClick = onDismiss) {
                        Text(text = "OK", color = Color(0xFF4CAF50), fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }
}