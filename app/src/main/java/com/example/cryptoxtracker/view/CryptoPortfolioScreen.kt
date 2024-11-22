package com.example.cryptoxtracker.view

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cryptoxtracker.viewmodel.CryptoScreenViewModel
import java.text.DecimalFormat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cryptoxtracker.model.CryptoCurrency
import com.example.cryptoxtracker.model.CryptoValues
import java.text.NumberFormat
import java.util.Locale

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
) {
    val coinListData by viewModel.coinListData.collectAsState()
    val isLoading = viewModel.isLoading


    if (isLoading.value) {
        // Display a full-screen loader
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    } else {
        if (coinListData.isSuccess) {
            val data = coinListData.getOrNull()
            if (!data.isNullOrEmpty()) {

                val totalHoldings = data.sumOf { crypto ->
                    val quantity = cryptoQuantitiesMap[crypto.id] ?: 0.0
                    crypto.currentPrice * quantity
                } ?: 0.0

                val cryptoWithPercentageList = data.map { crypto ->
                    val quantity = cryptoQuantitiesMap[crypto.id] ?: 0.0
                    val cryptoHoldingValue = crypto.currentPrice * quantity
                    val percentage = if (totalHoldings > 0) {
                        (cryptoHoldingValue / totalHoldings) * 100
                    } else {
                        0.0
                    }
                    Pair(crypto, percentage) // Create pair of crypto and percentage
                }.sortedByDescending { it.second } // Sort by percentage in descending order


                val totalHoldingsFormatted = formatWithCommasAndTwoDecimalPlaces(totalHoldings)
                HoldingsPortfolioScreen(
                    navController,
                    totalHoldingsFormatted,
                    totalHoldings,
                    4500000.09,
                    cryptoWithPercentageList,
                    cryptoQuantitiesMap
                )
                Text("YYYoooooooooooooooooooooooooooooooooooooooooooooo")
            } else {
                ErrorScreen()
            }
        } else if (coinListData.isFailure) {
            val error = coinListData.exceptionOrNull()?.message ?: "Unknown error"
            Text(text = "Error: $error", color = Color.Red)
        }
    }
}

@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Sorry.....No cryptocurrencies found!!!",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}

@Composable
fun HoldingsPortfolioScreen(
    navController: NavController,
    totalHoldings: String,
    totalHoldingDouble: Double,
    investedValue: Double,
    cryptoList: List<Pair<CryptoCurrency, Double>>,
    cryptoQuantitiesMapPassed: Map<String, Double>
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            //0xFF121212
            .background(Color(0xFF121212)) // Dark background
            .padding(horizontal = 16.dp)
    ) {

        // Portfolio Items List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                PortfolioCardCdcx(totalHoldings, totalHoldingDouble, investedValue)
                Spacer(modifier = Modifier.height(8.dp))
                TableHeader()
            }

            items(cryptoList) { (crypto, percentage) ->
                val qtity = cryptoQuantitiesMapPassed[crypto.id] ?: 0.0
                val cryptoHoldingValue = crypto.currentPrice * qtity
                PortfolioItemRow(
                    navController = navController,
                    crypto = crypto,
                    percentage = percentage,
                    qtity,
                    cryptoHoldingValue
                )
            }
        }
    }
}
@Composable
fun TableHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Name / Total Qty",
            color = Color.Gray,
            fontSize = 14.sp
        )
        Text(
            text = "Value / Returns (%)",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
fun PortfolioItemRow(
    navController: NavController,
    crypto: CryptoCurrency,
    percentage: Double,
    qtity: Double,
    cryptoHoldingValue: Double
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                //navController.navigate(Routes.detailScreen)
            }
            .background(Color(0xFF1E1E1E), RoundedCornerShape(6.dp))
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon and Name Column
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = crypto.image,
                contentDescription = "${crypto.name} logo",
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop
            )
            /*Image(
                painter = painterResource(id = item.iconResId),
                contentDescription = item.name,
                modifier = Modifier.size(32.dp)
            )*/
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = crypto.symbol.uppercase(),
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = "$qtity",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "${formatWithTwoDecimalPlaces(percentage)}%",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        val priceChangeColor = if (crypto.priceChangePercentage24h >= 0) {
            Color(0xFF3db284)
        } else {
            Color(0xFFF44336)
        }

        // Value and Returns Column
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(1f)
        ) {
            Row {
                Text(text = "₹", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = formatWithCommasAndTwoDecimalPlaces(cryptoHoldingValue), // Portfolio Value
                    color = Color.White,
                    fontSize = 14.sp,
                )
            }
            Row{
                Text(text = "24H: ", fontSize = 12.sp, color = Color.Gray)
                Text(
                    text =  "₹${formatWithCommasAndTwoDecimalPlaces(crypto.priceChange24h)} " + "(${formatWithTwoDecimalPlaces(crypto.priceChangePercentage24h)}%)",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = priceChangeColor
                )
            }
            Text(
                text = "LTP: ₹${formatWithCommasAndTwoDecimalPlaces(crypto.currentPrice)}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun PortfolioCardCdcx(portfolioValue: String, totalHoldingDouble: Double, investedValue: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF292929)) // Dark grey background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                text = "Portfolio Value",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Row {
                Text(text = "₹", color = Color.Gray, fontSize = 16.sp)
                Text(
                    text = portfolioValue, // Portfolio Value
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(color = Color(0xFF313131), thickness = 1.dp)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Invested Value",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Row {
                        Text(text = "₹", color = Color.Gray, fontSize = 14.sp)
                        Text(
                            text = formatWithCommasAndTwoDecimalPlaces(investedValue), // Invested Value
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Returns (%)",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    val returns = totalHoldingDouble - investedValue
                    val returnsPercentage = (returns/investedValue) * 100
                    val returnsColor = if (returns >= 0) Color(0xFF3db284) else Color.Red
                    Row {
                        Text(
                            text = "₹${formatWithCommasAndTwoDecimalPlaces(returns)} ", // Returns
                            color = returnsColor, // Green for positive returns
                            fontSize = 14.sp,
                        )
                        Text(text = " (${formatWithTwoDecimalPlaces(returnsPercentage)}%)", color = returnsColor, fontSize = 12.sp)
                    }
                }
            }

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1F1F1F))
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Button(
                onClick = { /* Deposit button action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F1F1F)),
                shape = RoundedCornerShape(6.dp),

                modifier = Modifier
                    .fillMaxWidth() // Makes the button stretch across the width
                    .height(50.dp)  // Adjust height to match the design
                    .padding(horizontal = 10.dp) // Padding on the sides
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(6.dp)
                    )
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Analyse Portfolio",
                    tint = Color.White
                )
                Text(text = "Analyze!", color = Color(0xFF6782e4))
            }
        }
    }
}



