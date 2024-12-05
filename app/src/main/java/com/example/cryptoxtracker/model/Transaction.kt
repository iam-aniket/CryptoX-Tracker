package com.example.cryptoxtracker.model

data class Transaction(
    val type: TransactionType,
    val date: String,
    val amount: Double,
    val coin: String
)