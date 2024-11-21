package com.example.cryptoxtracker.network

import com.example.cryptoxtracker.model.CryptoCurrency
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/Houses")
    suspend fun getCoinList(): List<String>

    @GET("ping")
    suspend fun pingCheck(
        @Query("x_cg_demo_api_key") apiKeyVal: String
    ): String

    @GET("coins/markets?vs_currency=inr")
    suspend fun getCryptocurrencies(
        @Query("x_cg_demo_api_key") apiKeyVal: String,
        @Query("ids") ids: String,
        @Query("vs_currency") curr: String, //inr
        @Query("order") order: String,  //market_cap_desc
        @Query("price_change_percentage") percentChange: String,    //24h
        @Query("precision") precision: String   //3
    ): retrofit2.Response<List<CryptoCurrency>>
}