package com.example.cryptoxtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.navigation.compose.rememberNavController
import com.example.cryptoxtracker.model.CryptoValues
import com.example.cryptoxtracker.ui.theme.CryptoXTrackerTheme
import com.example.cryptoxtracker.view.CryptoPortfolioScreen
import com.example.cryptoxtracker.viewmodel.CryptoScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoXTrackerTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) {

                }*/
                val navController = rememberNavController()
                CryptoPortfolioScreen(navController, CryptoScreenViewModel(), CryptoValues.cryptoQuantitiesMapFake)
            }
        }
    }
}

