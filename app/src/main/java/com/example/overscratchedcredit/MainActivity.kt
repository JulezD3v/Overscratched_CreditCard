package com.example.overscratchedcredit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.overscratchedcredit.commonUi.navigation.AppNavGraph
import com.example.overscratchedcredit.commonUi.theme.OverScratchedCreditTheme



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OverScratchedCreditTheme {
                AppNavGraph()
            }
        }
    }
}