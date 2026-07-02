package com.example.overscratchedcredit.camera.presentation.widgets

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.overscratchedcredit.commonUi.theme.PrimaryGreen

@Composable
fun ScanFrame(modifier: Modifier = Modifier) {
    val green = PrimaryGreen
    Canvas(modifier = modifier) {
        val strokeWidth = 6.dp.toPx()
        val cornerLen = 48.dp.toPx()

        // Top-left
        drawLine(green, Offset(0f, cornerLen), Offset(0f, 0f), strokeWidth)
        drawLine(green, Offset(0f, 0f), Offset(cornerLen, 0f), strokeWidth)
        // Top-right
        drawLine(green, Offset(size.width - cornerLen, 0f), Offset(size.width, 0f), strokeWidth)
        drawLine(green, Offset(size.width, 0f), Offset(size.width, cornerLen), strokeWidth)
        // Bottom-left
        drawLine(green, Offset(0f, size.height - cornerLen), Offset(0f, size.height), strokeWidth)
        drawLine(green, Offset(0f, size.height), Offset(cornerLen, size.height), strokeWidth)
        // Bottom-right
        drawLine(green, Offset(size.width, size.height - cornerLen), Offset(size.width, size.height), strokeWidth)
        drawLine(green, Offset(size.width - cornerLen, size.height), Offset(size.width, size.height), strokeWidth)
    }
}

@Preview(showBackground = true)
@Composable
private fun ScanFramePreview() {
    ScanFrame()
}