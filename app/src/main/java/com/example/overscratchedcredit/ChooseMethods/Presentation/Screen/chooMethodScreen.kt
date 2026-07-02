package com.example.overscratchedcredit.ChooseMethods.Presentation.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.overscratchedcredit.ChooseMethods.Presentation.Widgets.MethodOptionItem

@Composable
fun ChooseMethodScreen(
    onScanClick: () -> Unit,
    onManualClick: () -> Unit,
    onZuriClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.fillMaxWidth()
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 4.dp),
            color = Color.LightGray, // Uses your theme's variant color
            shape = RoundedCornerShape(12.dp), // Rounds the corners
            shadowElevation = 2.dp // Optional: Gives it a slight pop off the background
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                // verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Text(
                    text = "Choose Method",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }



    Spacer(Modifier.height(12.dp))
        MethodOptionItem(
            icon = Icons.Default.CameraAlt,
            title = "Scan voucher",
            subtitle = "AI reads scratched vouchers",
            onClick = onScanClick
        )
        Spacer(Modifier.height(12.dp))
        MethodOptionItem(
            icon = Icons.Default.Keyboard,
            title = "Enter manually",
            subtitle = "Type serial + partial PIN",
            onClick = onManualClick
        )
        Spacer(Modifier.height(12.dp))
        MethodOptionItem(
            icon = Icons.Default.AutoAwesome,
            title = "Ask Zuri (AI chat)",
            subtitle = "Chat with AI assistant",
            onClick = onZuriClick
        )
        Spacer(Modifier.height(12.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 4.dp),
            color = Color.LightGray, // Uses your theme's variant color
            shape = RoundedCornerShape(12.dp), // Rounds the corners
            shadowElevation = 2.dp // Optional: Gives it a slight pop off the background
        ){}
    }
}


@Preview(showBackground = true)
@Composable
private fun ChooseMethodScreenPreview() {
    ChooseMethodScreen(
        onScanClick = {},
        onManualClick = {},
        onZuriClick = {}
    )
}