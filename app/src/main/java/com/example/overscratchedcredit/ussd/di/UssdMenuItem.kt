package com.example.overscratchedcredit.ussd.di

data class UssdMenuItem(
    val number: String,
    val label: String,
    val onClick: () -> Unit
)