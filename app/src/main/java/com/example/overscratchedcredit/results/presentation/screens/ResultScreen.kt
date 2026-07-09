package com.example.overscratchedcredit.results.presentation.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.overscratchedcredit.commonUi.widgets.UssdStyleDialog
import com.example.overscratchedcredit.manual_input.RecoveryUiState
import com.example.overscratchedcredit.manual_input.presentation.ManualEntryVmclass

@Composable
fun ResultScreen(
    pin: String,
    amount: String = "KES 100",
    serialNumber: String = "1234567890123",
    partialPin: String = "1234",
    viewModel: ManualEntryVmclass = viewModel(),
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val formattedPin = pin.chunked(4).joinToString(" ")
    val ussdDialString = "*140*${amount.filter { it.isDigit() }}*$partialPin*$serialNumber#"

    val ussdState by viewModel.ussdState.collectAsState()
    var showUssdDialog by remember { mutableStateOf(false) }

    LaunchedEffect(ussdState) {
        if (ussdState is RecoveryUiState.Success) showUssdDialog = true
    }

    if (showUssdDialog) {
        val message = (ussdState as? RecoveryUiState.Success)?.pin ?: ""
        UssdStyleDialog(message = message, onDismiss = { showUssdDialog = false })
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF111111)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF111111))
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text("Recovery Result", color = Color.White, fontWeight = FontWeight.Bold,
                fontSize = 18.sp, modifier = Modifier.align(Alignment.Center))
        }

        Spacer(Modifier.height(16.dp))

        Surface(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(40.dp).background(Color(0xFFE8F5E9), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null,
                            tint = Color(0xFF4CAF50), modifier = Modifier.size(22.dp))
                    }
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text("Voucher Found!", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                        Text("PIN recovered successfully", fontSize = 13.sp, color = Color.Gray)
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFEEEEEE))

                Text("AMOUNT", fontSize = 11.sp, color = Color.Gray, letterSpacing = 1.sp)
                Spacer(Modifier.height(4.dp))
                Text(amount, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)

                Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFEEEEEE))

                Text("SERIAL NUMBER", fontSize = 11.sp, color = Color.Gray, letterSpacing = 1.sp)
                Spacer(Modifier.height(4.dp))
                Text(serialNumber, fontSize = 16.sp, color = Color.Black)

                Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFEEEEEE))

                Text("PIN (16 DIGITS — RECOVERED)", fontSize = 11.sp, color = Color.Gray, letterSpacing = 1.sp)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(formattedPin, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
                    OutlinedButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("PIN", pin))
                            Toast.makeText(context, "PIN copied", Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) { Text("Copy", fontSize = 13.sp) }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp)).padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("💡 Dial *141*$pin# to top up", fontSize = 13.sp, color = Color.Gray)
        }

        Spacer(Modifier.height(12.dp))


        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                .padding(vertical = 14.dp, horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Wrong serial, dial it on your phone",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(Modifier.height(6.dp))
               // Text(ussdDialString, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
            }
        }


        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = { /* TODO: out of MVP scope */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Save to My Vouchers",
                fontSize = 16.sp,
                color = Color.White
            ) }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ResultScreen(pin = "1234567890123456", amount = "KES 100",
        serialNumber = "1234567890123", partialPin = "1234", onBackClick = {})
}