package com.example.overscratchedcredit.manual_input.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import com.example.overscratchedcredit.commonUi.widgets.PrimaryButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.overscratchedcredit.manual_input.presentation.widget.PinGroupField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualScreen(
    onNavigateBack: () -> Unit,
    onRecoverClick: (serial: String, amount: String, partialPin: String, phone: String) -> Unit
) {
    // ── State ──────────────────────────────────────────────────────────────
    // Each var here is one piece of UI state.
    // When any of these change, Compose redraws only what is affected.
    var serialNumber by remember { mutableStateOf("") }
    var selectedAmount by remember { mutableStateOf("") }
    var pinGroup1 by remember { mutableStateOf("") }
    var pinGroup2 by remember { mutableStateOf("") }
    var pinGroup3 by remember { mutableStateOf("") }
    var pinGroup4 by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val amounts = listOf("KES 20", "KES 50", "KES 100", "KES 200", "KES 500", "KES 1000")

    // ── Layout ─────────────────────────────────────────────────────────────
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Enter Details",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                // Makes the screen scrollable if content is taller than the screen
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // ── Serial Number ──────────────────────────────────────────────
            InputSection(label = "VOUCHER SERIAL NUMBER") {
                OutlinedTextField(
                    value = serialNumber,
                    onValueChange = {
                        // Only update if the user has not exceeded 13 digits
                        if (it.length <= 13) serialNumber = it
                    },
                    placeholder = {
                        Text("Enter 13-digit serial number", color = Color.Gray)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )
            }

            // ── Voucher Amount Dropdown ────────────────────────────────────
            InputSection(label = "VOUCHER AMOUNT") {
                ExposedDropdownMenuBox(
                    expanded = dropdownExpanded,
                    onExpandedChange = { dropdownExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedAmount,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Select amount ▾", color = Color.Gray) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = dropdownExpanded
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = MaterialTheme.shapes.medium
                    )
                    ExposedDropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        amounts.forEach { amount ->
                            DropdownMenuItem(
                                text = { Text(amount) },
                                onClick = {
                                    selectedAmount = amount
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // ── Partial PIN Groups ─────────────────────────────────────────
            InputSection(
                label = "PARTIAL PIN (optional — enter what you can see)",
                subtitle = "Enter digits left to right. Leave blank if unreadable."
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PinGroupField(
                        value = pinGroup1,
                        onValueChange = { if (it.length <= 4) pinGroup1 = it },
                        label = "Group 1",
                        modifier = Modifier.weight(1f)
                    )
                    PinGroupField(
                        value = pinGroup2,
                        onValueChange = { if (it.length <= 4) pinGroup2 = it },
                        label = "Group 2",
                        modifier = Modifier.weight(1f),
                        isHighlighted = pinGroup2.isEmpty()
                    )
                    PinGroupField(
                        value = pinGroup3,
                        onValueChange = { if (it.length <= 4) pinGroup3 = it },
                        label = "Group 3",
                        modifier = Modifier.weight(1f)
                    )
                    PinGroupField(
                        value = pinGroup4,
                        onValueChange = { if (it.length <= 4) pinGroup4 = it },
                        label = "Group 4",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // ── Phone Number ───────────────────────────────────────────────
            InputSection(label = "YOUR SAFARICOM NUMBER") {
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { if (it.length <= 12) phoneNumber = it },
                    placeholder = { Text("07XX XXX XXX", color = Color.Gray) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // ── Recover Button ─────────────────────────────────────────────
            PrimaryButton(
                text = "Recover My PIN",
                onClick = {
                    // Combine the 4 groups into one partial PIN string
                    val partialPin = "$pinGroup1$pinGroup2$pinGroup3$pinGroup4"
                    onRecoverClick(serialNumber, selectedAmount, partialPin, phoneNumber)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ── Reusable Components ────────────────────────────────────────────────────────

// InputSection wraps a label + optional subtitle + any content below it.
// This is a higher-order function — it accepts a composable as a parameter.
@Composable
private fun InputSection(
    label: String,
    subtitle: String? = null,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
        content()
    }
}

@Preview
@Composable
private fun ManualScreenPreview() {
    ManualScreen(
        onNavigateBack = {},
        onRecoverClick = { _, _, _, _ -> }
    )
}
