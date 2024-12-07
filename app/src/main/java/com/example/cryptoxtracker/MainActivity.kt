package com.example.cryptoxtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptoxtracker.model.CryptoCurrency
import com.example.cryptoxtracker.model.SerializationUtil
import com.example.cryptoxtracker.repository.DataStoreManager
import com.example.cryptoxtracker.ui.theme.MyAppTheme
import com.example.cryptoxtracker.view.CoinDetailsScreen
import com.example.cryptoxtracker.view.CryptoPortfolioScreen
import com.example.cryptoxtracker.viewmodel.CoinDetailsViewModel
import com.example.cryptoxtracker.viewmodel.CryptoScreenViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            MyAppTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)) {
                    val navController = rememberNavController()

                    NavHost(navController, Routes.homeScreen, builder = {
                        composable(Routes.homeScreen) {
                            CryptoPortfolioScreen(
                                navController,
                                CryptoScreenViewModel(DataStoreManager(applicationContext))
                            )//, CryptoValues.cryptoQuantitiesMapFake)
                        }
                        composable(
                            route = Routes.detailScreen,
                            arguments = listOf(
                                navArgument("cryptoImg") { type = NavType.StringType },
                                navArgument("cryptoName") { type = NavType.StringType },
                                navArgument("cryptoSymbol") { type = NavType.StringType },
                                navArgument("price24h") { type = NavType.FloatType },
                                navArgument("priceChange24h") { type = NavType.FloatType },
                                navArgument("currentPrice") { type = NavType.FloatType },
                                navArgument("quantity") { type = NavType.FloatType },
                                navArgument("percentage") { type = NavType.FloatType },
                                navArgument("cryptoHoldingValue") { type = NavType.FloatType },
                                navArgument("id") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val cryptoImg = backStackEntry.arguments?.getString("cryptoImg")
                            val cryptoName = backStackEntry.arguments?.getString("cryptoName")
                            val cryptoSymbol = backStackEntry.arguments?.getString("cryptoSymbol")
                            val price24h =
                                backStackEntry.arguments?.getFloat("price24h")?.toDouble()
                            val priceChange24h =
                                backStackEntry.arguments?.getFloat("priceChange24h")?.toDouble()
                            val currentPrice =
                                backStackEntry.arguments?.getFloat("currentPrice")?.toDouble()
                            val quantity =
                                backStackEntry.arguments?.getFloat("quantity")?.toDouble()
                            val percentage =
                                backStackEntry.arguments?.getFloat("percentage")?.toDouble()
                            val cryptoHoldingValue =
                                backStackEntry.arguments?.getFloat("cryptoHoldingValue")?.toDouble()
                            val id =
                                backStackEntry.arguments?.getInt("id")

                            if (
                                cryptoImg != null &&
                                cryptoName != null &&
                                cryptoSymbol != null &&
                                price24h != null &&
                                priceChange24h != null &&
                                currentPrice != null &&
                                quantity != null &&
                                percentage != null &&
                                cryptoHoldingValue != null &&
                                id != null
                            ) {
                                CoinDetailsScreen(
                                    navController = navController,
                                    cryptoImg = cryptoImg,
                                    cryptoName = cryptoName,
                                    cryptoSymbol = cryptoSymbol,
                                    price24h = price24h,
                                    priceChange24h = priceChange24h,
                                    currentPrice = currentPrice,
                                    quantity = quantity,
                                    percentage = percentage,
                                    cryptoHoldingValue = cryptoHoldingValue,
                                    id = id
                                )
                            } else {
                                // Handle the error case (e.g., show an error screen or a fallback UI)
                                Text("Internal Error has occured, please retry")
                            }
                            //CoinDetailsScreen(CoinDetailsViewModel())
                        }
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComposable(navController: NavController) {
    TopAppBar(
        modifier = Modifier.background(Color.Black),
        title = {
            Text(text = "CryptoX Tracker", color = Color.White, fontSize = 20.sp)
        },
        navigationIcon = {
            IconButton(onClick = {/* TODO: Implement Onclick */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,  // Placeholder icon for app icon
                    contentDescription = "App Icon",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {/* TODO: Implement Onclick */ }) {
                Icon(
                    imageVector = Icons.Default.Search,  // Placeholder icon
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
            IconButton(onClick = {/* TODO: Implement Onclick */ }) {
                Icon(
                    imageVector = Icons.Default.Person,  // Placeholder icon
                    contentDescription = "Profile Icon",
                    tint = Color.White
                )
            }
            IconButton(onClick = {/* TODO: Implement Onclick */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,  // Placeholder icon
                    contentDescription = "More Options Icon",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

