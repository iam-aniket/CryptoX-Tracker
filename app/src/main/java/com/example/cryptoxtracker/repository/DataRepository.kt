package com.example.cryptoxtracker.repository

import android.util.Log
import com.example.cryptoxtracker.model.CryptoCurrency
import com.example.cryptoxtracker.network.ApiService

class DataRepository(private val apiService: ApiService) {
    private val APIKEY = "CG-cADMVJyJqucmmmNkLoPimPiL"
    //28 coins
    private val coinListStrings = "bitcoin,aptos,avalanche-2,arbitrum,dogecoin,polygon-ecosystem-token,polkadot,fetch-ai,chainlink,near,pepe,tether,floki,dogwifcoin,book-of-meme,tron,ecash,1inch,binancecoin,harmony,solana,bittorrent,ethereum,brett,cats-2,cardano,cosmos,optimism,render-token,jupiter,worldcoin-wld,kaspa,ondo-finance,we-love-t,gigachad-2"

    suspend fun getDataFromApi(): Result<List<CryptoCurrency>> {
        return try {
            // API Call
            val response = apiService.getCryptocurrencies(
                APIKEY,
                coinListStrings,
                "inr",
                "market_cap_desc",
                "24h",
                "3"
            )
            Log.e("XYZ","API CALL....................................")

            if (response.isSuccessful) {
                Log.e("XYZ","SUCCESS....................................")
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Log.e("XYZ","FAILURE....................................")
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}