package com.example.cryptoxtracker.view

import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cryptoxtracker.R
import com.example.cryptoxtracker.model.CryptoCurrency
import com.example.cryptoxtracker.model.CryptoValues
import com.example.cryptoxtracker.viewmodel.CryptoScreenViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.text.DecimalFormat

fun formatWithCommasAndTwoDecimalPlaces(value: Double): String {
    val decimalFormat = DecimalFormat("#,##,##0.00")
    return decimalFormat.format(value)
}

fun formatWithTwoDecimalPlaces(value: Double): String {
    val decimalFormat = DecimalFormat("#0.00")
    return decimalFormat.format(value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoPortfolioScreen(
    navController: NavController,
    viewModel: CryptoScreenViewModel = viewModel(),
) {
    val coinQuantityList by viewModel.coinQuantityList.collectAsState(initial = CryptoValues.cryptoQuantitiesMapFake)
    val editableCoinQuantityList =
        remember(coinQuantityList) { coinQuantityList.toMutableMap() } // Initialize properly
    var editMode by remember { mutableStateOf(false) }

    val coinListData by viewModel.coinListData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredCoins by viewModel.filteredCoinList.collectAsState()

    // State to toggle search mode
    var isSearchMode by remember { mutableStateOf(false) }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    } else {
        if (filteredCoins.isSuccess) {
            TopAppBar(
                modifier = Modifier.background(Color.Black),
                title = {
                    if (isSearchMode) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp, vertical = 8.dp)
                        ) {
                            TextField(
                                value = searchQuery,
                                onValueChange = { viewModel.updateSearchQuery(it) },
                                placeholder = { Text("Search...") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White, shape = RoundedCornerShape(24.dp)),
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    cursorColor = Color.Black,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black
                                )
                            )
                        }
                    } else {
                        Text(text = "CryptoX Tracker", color = Color.White, fontSize = 20.sp)
                    }
                },
                navigationIcon = {
                    if (isSearchMode) {
                        IconButton(onClick = {
                            isSearchMode = false
                            viewModel.updateSearchQuery("")
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    } else
                        IconButton(onClick = {/* TODO: Implement Onclick */ }) {
                            Icon(
                                imageVector = Icons.Default.Menu,  // Placeholder icon for app icon
                                contentDescription = "App Icon",
                                tint = Color.White
                            )
                        }
                },
                actions = {
                    if (!isSearchMode) {
                        IconButton(onClick = { isSearchMode = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                    }
                    IconButton(onClick = { viewModel.togglePortfolioVisibility() }) {
                        val visibilityIcon = if (viewModel.isPortfolioVisible.value) {
                            painterResource(id = R.drawable.visibleeye30) // Eye icon
                        } else {
                            painterResource(id = R.drawable.closedeye50) // Eye-off icon
                        }
                        Icon(visibilityIcon, contentDescription = "Toggle Visibility", tint = Color.White)
                    }
                    IconButton(onClick = {
                        editMode = !editMode
                        if (!editMode) {
                            // Save changes and exit edit mode
                            viewModel.updateCoinDetails(editableCoinQuantityList)
                        }
                    }) {
                        Icon(
                            imageVector = if (editMode) Icons.Default.Done else Icons.Default.Edit,
                            contentDescription = if (editMode) "Save Changes" else "Edit",
                            tint = Color.White
                        )
                    }
                },
            )

            val data = filteredCoins.getOrNull()
            if (!data.isNullOrEmpty()) {

                val totalHoldings = data.sumOf { crypto ->
                    val quantity = editableCoinQuantityList[crypto.id] ?: 0.0
                    crypto.currentPrice * quantity
                } ?: 0.0
                Log.e("UI", "$editableCoinQuantityList")
                val cryptoWithPercentageList = data.map { crypto ->
                    val quantity = editableCoinQuantityList[crypto.id] ?: 0.0
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
                    editableCoinQuantityList,
                    cryptoWithPercentageList,
                    editMode,
                    isLoading,
                    viewModel
                )
            } else {
                ErrorScreen()
            }
        } else if (filteredCoins.isFailure) {
            val error = filteredCoins.exceptionOrNull()?.message ?: "Unknown error"
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
    editableCoinDetails: MutableMap<String, Double>,
    cryptoList: List<Pair<CryptoCurrency, Double>>,
    editMode: Boolean,
    isLoading : Boolean,
    viewModel: CryptoScreenViewModel
) {

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    /*SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.fetchItems() // Trigger data reload
        }
    ){

    }
*/
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
                val qtity = editableCoinDetails[crypto.id] ?: 0.0
                val cryptoHoldingValue = crypto.currentPrice * qtity
                PortfolioItemRow(
                    navController = navController,
                    crypto = crypto,
                    percentage = percentage,
                    qtity,
                    onValueChange = { newValue ->
                        editableCoinDetails[crypto.id] = newValue
                    },
                    cryptoHoldingValue,
                    editMode,
                    viewModel
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
    initialValue: Double,
    onValueChange: (Double) -> Unit,
    cryptoHoldingValue: Double,
    isEditMode: Boolean,
    viewModel: CryptoScreenViewModel
) {
    var valueText by remember { mutableStateOf(initialValue.toString()) }

    val displayCryptoQuantity = if (viewModel.isPortfolioVisible.value) {
        initialValue
    } else {
        "**"
    }

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
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = crypto.symbol.uppercase(),
                    fontSize = 14.sp,
                    color = Color.White
                )
                if (isEditMode) {
                    TextField(
                        value = valueText,
                        onValueChange = { newText ->
                            valueText = newText
                            val newValue = newText.toDoubleOrNull()
                            if (newValue != null) {
                                onValueChange(newValue)
                            }
                        },
                        modifier = Modifier
                            .height(45.dp) // Reduced height
                            .background(Color(0xFF292929), shape = RoundedCornerShape(4.dp)),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(color = Color.Gray, fontSize = 14.sp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFFFFFFF),
                            focusedContainerColor = Color(0xFFFFFFFF),
                            unfocusedIndicatorColor = Color(0xFF292929),
                            focusedIndicatorColor = Color.Gray,
                            cursorColor = Color.Gray,
                            focusedTextColor = Color.Black
                        ),
                        singleLine = true
                    )
                } else {
                    Text(
                        text = "$displayCryptoQuantity",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
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

        val displayCryptoHoldingValue = if (viewModel.isPortfolioVisible.value) {
            formatWithCommasAndTwoDecimalPlaces(cryptoHoldingValue)
        } else {
            "** ** ** ***"
        }

        // Value and Returns Column
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(1f)
        ) {
            Row {
                Text(text = "₹", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = displayCryptoHoldingValue, // Portfolio Value
                    color = Color.White,
                    fontSize = 14.sp,
                )
            }
            Row {
                Text(text = "24H: ", fontSize = 12.sp, color = Color.Gray)
                Text(
                    text = "₹${formatWithCommasAndTwoDecimalPlaces(crypto.priceChange24h)} " + "(${
                        formatWithTwoDecimalPlaces(
                            crypto.priceChangePercentage24h
                        )
                    }%)",
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
                    val returnsPercentage = (returns / investedValue) * 100
                    val returnsColor = if (returns >= 0) Color(0xFF3db284) else Color.Red
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



