package com.example.cryptoxtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.example.cryptoxtracker.model.CryptoValues
import com.example.cryptoxtracker.ui.theme.CryptoXTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoXTrackerTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) {

                }*/
                Text(CryptoValues.cryptoQuantitiesMapFake.toString())
            }
        }
    }
}

