package com.example.cryptoxtracker.repository

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.cryptoxtracker.model.CryptoValues
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Define the DataStore instance
val Context.dataStore by preferencesDataStore(name = "coin_preferences")

class DataStoreManager(private val context: Context) {

    private val coinDetailsKey = doublePreferencesKey("coin_details")

    // Read DataStore value
    fun getCoinDetails(): Flow<MutableMap<String, Double>> {
        return context.dataStore.data
            .catch { exception ->
                // Handle exceptions gracefully
                if (exception is IOException) emit(emptyPreferences())
            }
            .map { preferences ->
                // Retrieve the value, or return an empty map if not found
                val coinMap = mutableMapOf<String, Double>()
                preferences.asMap().forEach { (key, value) ->
                    if (value is Double && key.name.startsWith("coin_details")) {
                        coinMap[key.name.removePrefix("coin_details.")] = value
                    }
                }
                // If no data in DataStore, return default values
                if (coinMap.isEmpty()) {
                    CryptoValues.cryptoQuantitiesMap
                } else {
                    coinMap
                }
            }
    }

    // Write DataStore value
    suspend fun updateCoinDetails(coinDetails: MutableMap<String, Double>) {
        context.dataStore.edit { preferences ->
            preferences.clear() // Clear previous data
            coinDetails.forEach { (key, value) ->
                preferences[doublePreferencesKey("coin_details.$key")] = value
            }
        }
    }
}
