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
import androidx.navigation.compose.rememberNavController
import com.example.cryptoxtracker.repository.DataStoreManager
import com.example.cryptoxtracker.ui.theme.MyAppTheme
import com.example.cryptoxtracker.view.CryptoPortfolioScreen
import com.example.cryptoxtracker.viewmodel.CryptoScreenViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            MyAppTheme {
                Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
                    val navController = rememberNavController()

                    //Added Top App bar in Crypto Portfolio Screen Itself - So removed here
                    //TopAppBarComposable(navController)

                    CryptoPortfolioScreen(navController, CryptoScreenViewModel(DataStoreManager(applicationContext)))//, CryptoValues.cryptoQuantitiesMapFake)
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

