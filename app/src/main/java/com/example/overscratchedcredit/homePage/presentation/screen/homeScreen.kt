package com.example.overscratchedcredit.homePage.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.overscratchedcredit.commonUi.widgets.PrimaryButton
import com.example.overscratchedcredit.commonUi.widgets.SecondaryButton

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRecoverClick: () -> Unit,
    onUssdDemoClick: () -> Unit,
    onHowItWorksClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App icon placeholder
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFEAEAEA))
                .border(1.dp, Color.Gray, RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("APP ICON", color = Color.Gray, fontSize = 12.sp)
        }

        Spacer(Modifier.height(24.dp))

        Text("Voucher Recovery", fontWeight = FontWeight.Bold, fontSize = 26.sp)

        Spacer(Modifier.height(8.dp))

        Text(
            "Recover your overscratched \n Safaricom airtime voucher",
            color = Color.Gray,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))

        PrimaryButton(text = "Recover a voucher", onClick = onRecoverClick)

        Spacer(Modifier.height(12.dp))

        SecondaryButton(text = "USSD Demo", onClick = onUssdDemoClick)

        Spacer(Modifier.height(12.dp))

        SecondaryButton(text = "How it works", onClick = onHowItWorksClick)

        Spacer(Modifier.height(20.dp))

        Text("🔒 Secure & Private", color = Color.Gray, fontSize = 13.sp)
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview () {
    HomeScreen(
        onRecoverClick = {},
        onUssdDemoClick = {},
        onHowItWorksClick = {}
    )
}
