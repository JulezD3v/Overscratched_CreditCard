package com.example.overscratchedcredit.manual_input.presentation.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


    @Composable
    fun PinGroupField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        modifier: Modifier = Modifier,
        isHighlighted: Boolean = false
    ) {
        val borderColor = when {
            isHighlighted -> Color.Red.copy(alpha = 0.6f)
            else -> Color.LightGray
        }

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                fontSize = 10.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                ),
                placeholder = {
                    Text(
                        text = if (isHighlighted) "??" else "····",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = if (isHighlighted) Color.Red.copy(alpha = 0.6f)
                        else Color.LightGray
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            )
        }
    }

@Preview
@Composable
private fun PinGroupFieldPreview() {
    PinGroupField(
        value = "1234",
        onValueChange = {},
        label = "Group 1",
        isHighlighted = true
    )
}

