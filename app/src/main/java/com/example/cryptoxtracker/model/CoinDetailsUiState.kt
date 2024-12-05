package com.example.cryptoxtracker.model

data class CoinDetailsUiState(
    val currentValue: Double,
    val invested: Double,
    val returns: Double,
    val returnPercentage: Double,
    val avgBuyingPrice: Double,
    val lastTradedPrice: Double,
    val transactions: List<Transaction>
)