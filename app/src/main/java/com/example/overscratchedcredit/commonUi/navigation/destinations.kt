package com.example.overscratchedcredit.commonUi.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Destination : NavKey

@Serializable
data object Splash : Destination

@Serializable
data object Home : Destination

@Serializable
data object ChooseMethod : Destination

@Serializable
data object Scanner : Destination

@Serializable
data object ManualEntry : Destination

@Serializable
data object Zuri : Destination

@Serializable
data class PinResult(val pin: String) : Destination
