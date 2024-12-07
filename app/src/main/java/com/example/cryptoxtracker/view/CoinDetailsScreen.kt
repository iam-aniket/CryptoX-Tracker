package com.example.cryptoxtracker.view

import android.health.connect.datatypes.units.Percentage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cryptoxtracker.R
import com.example.cryptoxtracker.model.CoinDetailsUiState
import com.example.cryptoxtracker.model.CryptoCurrency
import com.example.cryptoxtracker.model.Transaction
import com.example.cryptoxtracker.model.TransactionType
import com.example.cryptoxtracker.viewmodel.CoinDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsScreen(
    navController: NavController,
    cryptoImg: String,
    cryptoName: String,
    cryptoSymbol: String,
    price24h: Double,
    priceChange24h: Double,
    currentPrice: Double,
    quantity: Double,
    percentage: Double,
    cryptoHoldingValue: Double,
    id : Int
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = cryptoImg,
                            contentDescription = "Coin Logo",
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Gray, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = cryptoSymbol.uppercase(),
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = cryptoName,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle more options */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More Options")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF292929))
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* Handle Buy */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A83F1)) // Blue button
                ) {
                    Text(text = "Buy")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { /* Handle Sell */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)) // Red button
                ) {
                    Text(text = "Sell")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            //49 random numbers - assign based on position
            val arr = intArrayOf(8, 11, 3, 17, 7, 26, 15, 8, 11, 3, 17, 7, 26, 15, 8, 11, 3, 17, 7, 26, 15, 8, 11, 3, 17, 7, 26, 15, 8, 11, 3, 17, 7, 26, 15, 8, 11, 3, 17, 7, 26, 15, 8, 11, 3, 17, 7, 26, 15)
            val investedValue = (cryptoHoldingValue - cryptoHoldingValue / arr[id])

            CoinDetailsHeader(cryptoHoldingValue, investedValue, currentPrice)

            //CoinDetailsHeader()
            Spacer(modifier = Modifier.height(16.dp))
            TotalQuantityCard(cryptoHoldingValue, investedValue, quantity, cryptoSymbol)
            //PortfolioCardCdcx2(43456.9, 7890.89)


            Spacer(modifier = Modifier.height(16.dp))
            TransactionList(cryptoSymbol.uppercase())
        }
    }
}

@Composable
fun TotalQuantityCard(totalHoldingDouble: Double, investedValue: Double, quantity: Double, cryptoSymbol: String) {
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
                text = "Total Quantity",
                color = Color.Gray,
                fontSize = 14.sp
            )

            val currentValue = formatWithCommasAndTwoDecimalPlaces(totalHoldingDouble)
            Row {
                Text(
                    text = formatWithSixDecimalPlaces(quantity), // Portfolio Value
                    color = Color.White,
                    fontSize = 16.sp,
                )
                Spacer(Modifier.width(4.dp))
                Text(text = cryptoSymbol.uppercase(), color = Color.Gray, fontSize = 16.sp)
            }

            Text(
                text = "≈ ₹ $currentValue",
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(color = Color(0xFF313131), thickness = 1.dp)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Available",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Row {
                        Text(
                            text = formatWithSixDecimalPlaces(0.00), // Portfolio Value
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(text = cryptoSymbol.uppercase(), color = Color.Gray, fontSize = 12.sp)
                    }

                    Text(text = "≈ ₹ ${formatWithCommasAndTwoDecimalPlaces(2.07)}", color = Color.Gray, fontSize = 12.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Locked",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Row {
                        Text(
                            text = formatWithSixDecimalPlaces(quantity), // Portfolio Value
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(text = cryptoSymbol.uppercase(), color = Color.Gray, fontSize = 12.sp)
                    }

                    Text(text = "≈ ₹ $currentValue", color = Color.Gray, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TransactionList(cryptoSymbol: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Transactions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        TransactionItem(
            type = "Sell",
            date = "16 Sep 2024, 6:04 PM",
            amount = "0.44 $cryptoSymbol",
            status = "Completed"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TransactionItem(
            type = "Interest from Earn",
            date = "09 Aug 2024, 1:33 AM",
            amount = "0.032 $cryptoSymbol",
            status = "Credited"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TransactionItem(
            type = "Sell",
            date = "09 Aug 2024, 7:02 AM",
            amount = "0.001 $cryptoSymbol",
            status = "Completed"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TransactionItem(
            type = "Buy",
            date = "29 Jul 2024, 10:12 AM",
            amount = "0.009 $cryptoSymbol",
            status = "Completed"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TransactionItem(
            type = "Buy",
            date = "22 Jul 2024, 4:34 PM",
            amount = "1.4 $cryptoSymbol",
            status = "Completed"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TransactionItem(
            type = "Sell",
            date = "22 Jul 2024, 11:43 AM",
            amount = "0.001 $cryptoSymbol",
            status = "Completed"
        )
        Spacer(modifier = Modifier.height(4.dp))
        TransactionItem(
            type = "Buy",
            date = "20 Jul 2024, 11:35 AM",
            amount = "0.001 $cryptoSymbol",
            status = "Completed"
        )
    }
}

@Composable
fun TransactionItem(type: String, date: String, amount: String, status: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF292929)) // Dark grey background
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = if (type == "Sell") Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = if (type != "Sell") Color(0xFF4CAF50) else Color.Red,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = type, color = Color.White, fontSize = 14.sp)
                Text(
                    text = date,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = amount, color = Color.White, fontSize = 14.sp)
                Text(text = status.uppercase(), color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CoinDetailsHeader(totalHoldingDouble: Double, investedValue: Double, currentPrice: Double) {
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
                text = "Current Value",
                color = Color.Gray,
                fontSize = 14.sp
            )

            val currentValue = formatWithCommasAndTwoDecimalPlaces(totalHoldingDouble)
            Row {
                Text(text = "₹", color = Color.Gray, fontSize = 16.sp)
                Text(
                    text = currentValue, // Portfolio Value
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
                        text = "Invested",
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
                    val returnsPercentage = (returns / investedValue) * 100
                    val returnsColor = if (returns >= 0) Color(0xFF3db284) else Color(0xFFF44336)
                    Row {
                        Text(
                            text = "₹${formatWithCommasAndTwoDecimalPlaces(returns)} ", // Returns
                            color = returnsColor, // Green for positive returns
                            fontSize = 14.sp,
                        )
                        Text(
                            text = " (${formatWithTwoDecimalPlaces(returnsPercentage)}%)",
                            color = returnsColor,
                            fontSize = 12.sp
                        )
                    }
                }
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
                        text = "Avg. Buying Price",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Row {
                        Text(text = "₹", color = Color.Gray, fontSize = 14.sp)
                        Text(
                            text = formatWithCommasAndTwoDecimalPlaces(currentPrice/1.5), // Invested Value
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Last Traded Price",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Row {
                        Text(text = "₹", color = Color.Gray, fontSize = 14.sp)
                        Text(
                            text = formatWithCommasAndTwoDecimalPlaces(currentPrice), // Invested Value
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}