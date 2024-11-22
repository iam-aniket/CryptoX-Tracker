package com.example.cryptoxtracker.repository

import com.example.cryptoxtracker.model.CryptoCurrency
import com.example.cryptoxtracker.network.ApiService

class DataRepository(private val apiService: ApiService) {
    private val APIKEY = "CG-cADMVJyJqucmmmNkLoPimPiL"
    //28 coins
    private val coinListStrings = "bitcoin,aptos,avalanche-2,arbitrum,dogecoin,polygon-ecosystem-token,polkadot,fetch-ai,chainlink,near,pepe,tether,floki,dogwifcoin,book-of-meme,tron,ecash,1inch,binancecoin,harmony,solana,bittorrent,ethereum,cosmos,optimism,render-token,jupiter-exchange-solana,worldcoin-wld"

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
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}