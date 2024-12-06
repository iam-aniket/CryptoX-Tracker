package com.example.cryptoxtracker.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cryptoxtracker.R
import com.example.cryptoxtracker.model.CoinDetailsUiState
import com.example.cryptoxtracker.model.Transaction
import com.example.cryptoxtracker.model.TransactionType
import com.example.cryptoxtracker.viewmodel.CoinDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.cryptox_app_icon),
                            contentDescription = "BNB Icon",
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "BNB Binance Coin",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
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
            PortfolioCardCdcx2("45,793.92", 43456.9, 7890.89)

            //CoinDetailsHeader()
            Spacer(modifier = Modifier.height(16.dp))
            PortfolioCardCdcx2("45,793.92", 43456.9, 7890.89)

            //TotalQuantityCard()

            Spacer(modifier = Modifier.height(16.dp))
            TransactionList() // Placeholder for transaction history
        }
    }
}

@Composable
fun CoinDetailsHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Current Value",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "₹4,793.92",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Invested", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "₹3,675.00", style = MaterialTheme.typography.bodyLarge)
                }
                Column {
                    Text(text = "Returns (%)", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "₹1,118.92 (30.45%)",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF4CAF50) // Green color
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Avg. Buying Price", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "₹49,000.00", style = MaterialTheme.typography.bodyLarge)
                }
                Column {
                    Text(text = "Last Traded Price", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "₹63,918.97", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
fun TotalQuantityCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Total Quantity",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "0.075 BNB",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "≈ ₹4,793.92",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun TransactionList() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Transactions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        TransactionItem2(
            type = "Sell",
            date = "03 Aug 2024, 4:04 AM",
            amount = "0.061 BNB",
            status = "Completed"
        )
        TransactionItem2(
            type = "Buy",
            date = "03 Aug 2024, 4:03 AM",
            amount = "0.071 BNB",
            status = "Completed"
        )
        TransactionItem2(
            type = "Sell",
            date = "03 Aug 2024, 4:02 AM",
            amount = "0.001 BNB",
            status = "Completed"
        )
        TransactionItem2(
            type = "Buy",
            date = "03 Aug 2024, 4:01 AM",
            amount = "0.001 BNB",
            status = "Completed"
        )

        // Add more transactions as required
    }
}

@Composable
fun TransactionItem2(type: String, date: String, amount: String, status: String) {
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
                imageVector = if (type == "Sell") Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                tint = if (type == "Buy") Color(0xFF4CAF50) else Color.Red,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = type, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(text = amount, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = status,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun TransactionItem(type: String, date: String, amount: String, status: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = Color(0xFF4CAF50), // Green
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = type, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(text = amount, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = status,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PortfolioCardCdcx2(currentValue: String, totalHoldingDouble: Double, investedValue: Double) {
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
                            text = formatWithCommasAndTwoDecimalPlaces(investedValue), // Invested Value
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
                            text = formatWithCommasAndTwoDecimalPlaces(investedValue), // Invested Value
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsScreen(viewModel: CoinDetailsViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { /* Handle Back Navigation */ }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.cryptox_app_icon), // Replace with actual coin image
                            contentDescription = "Coin Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "BNB Binance Coin",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle Options */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* Handle Buy Action */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Buy")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { /* Handle Sell Action */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
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
                .verticalScroll(rememberScrollState())
        ) {
            CoinOverviewSection(uiState)
            CoinTransactionSection(uiState.transactions)
        }
    }
}

@Composable
fun CoinOverviewSection(uiState: CoinDetailsUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Current Value", style = MaterialTheme.typography.titleMedium)
            Text(text = "₹${uiState.currentValue}", style = MaterialTheme.typography.headlineSmall)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = "Invested", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "₹${uiState.invested}", style = MaterialTheme.typography.bodyLarge)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Returns (%)", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "₹${uiState.returns} (${uiState.returnPercentage}%)",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Green
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = "Avg. Buying Price", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "₹${uiState.avgBuyingPrice}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Last Traded Price", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "₹${uiState.lastTradedPrice}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun CoinTransactionSection(transactions: List<Transaction>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Transactions", style = MaterialTheme.typography.titleMedium)
            transactions.forEach { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (transaction.type == TransactionType.Buy) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = transaction.type.name,
                tint = if (transaction.type == TransactionType.Buy) Color.Green else Color.Red
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = transaction.type.name, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = transaction.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
        Text(
            text = "${transaction.amount} ${transaction.coin}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
