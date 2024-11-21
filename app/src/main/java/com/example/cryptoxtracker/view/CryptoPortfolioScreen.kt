package com.example.cryptoxtracker.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
    val coinListData by viewModel.coinListData.observeAsState()


    /*when (coinListData) {
        is Result.Success -> {
            val data = (cryptoState as Result.Success).value
            LazyColumn {
                items(data) { crypto ->
                    Text(text = crypto.name)
                }
            }
        }
        is Result.Failure -> {
            val error = (cryptoState as Result.Failure).exceptionOrNull()?.message ?: "Unknown error"
            Text(text = "Error: $error", color = Color.Red)
        }
        else -> {
            Text(text = "Loading...")
        }
    }*/

    Text(coinListData.toString())
}