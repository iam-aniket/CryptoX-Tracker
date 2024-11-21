package com.example.cryptoxtracker.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.cryptoxtracker.viewmodel.CryptoScreenViewModel
import java.text.DecimalFormat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cryptoxtracker.model.CryptoValues

fun formatWithCommasAndTwoDecimalPlaces(value: Double): String {
    val decimalFormat = DecimalFormat("#,##,##0.00")
    return decimalFormat.format(value)
}

fun formatWithTwoDecimalPlaces(value: Double): String {
    val decimalFormat = DecimalFormat("#0.00")
    return decimalFormat.format(value)
}

@Composable
fun CryptoPortfolioScreen(
    navController: NavController,
    viewModel: CryptoScreenViewModel = viewModel(),
    cryptoQuantitiesMap: Map<String, Double>
){
    Text(cryptoQuantitiesMap.toString())
}