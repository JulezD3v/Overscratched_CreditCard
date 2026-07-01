package com.example.overscratchedcredit.splashscreen.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.width
import com.example.overscratchedcredit.commonUi.theme.PrimaryGreen
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import com.example.overscratchedcredit.commonUi.theme.Secondary
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.overscratchedcredit.commonUi.theme.Neutral
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
//import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SplashScreen() {
    val progress by remember { mutableFloatStateOf(0.4f) } // Manage this via ViewModel


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Secondary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo Section
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Circle, contentDescription = null, tint = PrimaryGreen)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Safaricom", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text(" Nexus", color = PrimaryGreen, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(100.dp))

        // Progress Bar
        LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .width(250.dp)
            .clip(RoundedCornerShape(4.dp)),
        color = PrimaryGreen,
        trackColor = Neutral,
        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
        )

        // Footer
        Spacer(modifier = Modifier.height(20.dp))
        Text("SECURE ENCRYPTED CHANNEL", color = Color.Gray, fontSize = 12.sp, letterSpacing = 2.sp)
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}