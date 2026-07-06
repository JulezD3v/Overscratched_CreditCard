// lowercase package — Camera → camera
package com.example.overscratchedcredit.camera.presentation.screens

import android.Manifest // ← correct: Android permissions manifest, not java.util.jar
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Icon // ← correct: material3 Icon, not SegmentedButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text // ← correct: Compose Text, not org.w3c.dom.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.overscratchedcredit.camera.presentation.widgets.CameraPreview
import com.example.overscratchedcredit.camera.presentation.widgets.ScanFrame
import com.example.overscratchedcredit.commonUi.theme.PrimaryGreen

@Composable
fun ScannerScreen(
    onBackClick: () -> Unit,
    onManualEntryClick: () -> Unit,
    onShutterClick: () -> Unit // ← new: nav graph uses this to push Result
) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    // tracks whether flash is on or off
    var flashEnabled by remember { mutableStateOf(false) }

    // checks if CAMERA permission is already granted when the screen first loads
    var hasCameraPermission by remember {
        mutableStateOf(
            if (isPreview) false
            else ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // this fires the system permission dialog when called
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasCameraPermission = granted }

    // on first composition, request permission if not already granted
    LaunchedEffect(Unit) {
        if (!isPreview && !hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ── Top bar ──────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF111111))
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                // AutoMirrored: flips the arrow on RTL languages (Arabic, Hebrew etc)
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                "Scan Voucher",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // ── Subtitle ─────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A1A1A))
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Place PIN area inside the frame", color = Color.Gray, fontSize = 14.sp)
        }

        // ── Camera area ────────────────────────────────────

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            // real device with permission → live camera feed
            // preview / no permission → dark placeholder box
            if (!isPreview && hasCameraPermission) {
                CameraPreview(modifier = Modifier.fillMaxSize())
            } else {
                Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0D0D0D)))
            }

            // ScanFrame draws the green corner brackets on top of the camera
            ScanFrame(
                modifier = Modifier
                    .fillMaxWidth(0.78f)
                    .fillMaxHeight(0.48f)
            )
        }

        // ── Scanning indicator ───────────────────────────────
        Text(
            "— scanning —",
            color = PrimaryGreen,
            fontSize = 13.sp,
            modifier = Modifier.padding(vertical = 14.dp)
        )

        // ── Controls row ─────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF111111))
                .padding(horizontal = 40.dp, vertical = 20.dp)
        ) {
            // Flash toggle
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color(0xFF2A2A2A), CircleShape)
                    .clickable { flashEnabled = !flashEnabled }
                    .align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (flashEnabled) Icons.Default.FlashOn else Icons.Default.FlashOff,
                    contentDescription = "Flash",
                    tint = if (flashEnabled) Color.Yellow else Color.White
                )
            }

            // Shutter — calls onShutterClick which nav graph uses to push Result
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .border(3.dp, Color.White, CircleShape)
                    .padding(6.dp)
                    .background(Color.White.copy(alpha = 0.25f), CircleShape)
                    .clickable { onShutterClick() }
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "SHUTTER",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Gallery picker — stub for now
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color(0xFF2A2A2A), CircleShape)
                    .clickable { /* TODO: gallery picker */ }
                    .align(Alignment.CenterEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Photo, contentDescription = "Gallery", tint = Color.White)
            }
        }

        // ── Footer ───────────────────────────────────────────
        Text(
            "Can't scan? Enter manually",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier
                .clickable { onManualEntryClick() }
                .padding(vertical = 18.dp)
        )
    }
}

// Preview: no camera hardware, so dark placeholder renders instead
@Preview(showBackground = true)
@Composable
fun ScannerScreenPreview() {
    ScannerScreen(
        onBackClick = {},
        onManualEntryClick = {},
        onShutterClick = {}
    )
}