package com.example.cryptoxtracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptoxtracker.model.CoinDetailsUiState
import com.example.cryptoxtracker.model.Transaction
import com.example.cryptoxtracker.model.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CoinDetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        CoinDetailsUiState(
            currentValue = 4793.92,
            invested = 3675.00,
            returns = 1118.92,
            returnPercentage = 30.45,
            avgBuyingPrice = 49000.00,
            lastTradedPrice = 63918.97,
            transactions = listOf(
                Transaction(TransactionType.Buy, "03 Aug 2024, 4:04 AM", 0.061, "BNB"),
                Transaction(TransactionType.Buy, "01 Aug 2024, 10:00 AM", 0.014, "BNB")
            )
        )
    )
    val uiState: StateFlow<CoinDetailsUiState> = _uiState.asStateFlow()
}
