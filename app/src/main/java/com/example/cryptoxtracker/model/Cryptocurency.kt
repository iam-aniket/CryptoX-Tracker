package com.example.cryptoxtracker.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CryptoCurrency(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @Json(name = "current_price") val currentPrice: Double,
    @Json(name = "market_cap") val marketCap: Long,
    @Json(name = "market_cap_rank") val marketCapRank: Int?,
    @Json(name = "total_volume") val totalVolume: Long,
    @Json(name = "high_24h") val high24h: Double,
    @Json(name = "low_24h") val low24h: Double,
    @Json(name = "price_change_24h") val priceChange24h: Double,
    @Json(name = "price_change_percentage_24h") val priceChangePercentage24h: Double,
    @Json(name = "market_cap_change_percentage_24h") val marketCapChangePercentage24h: Double,
    @Json(name = "circulating_supply") val circulatingSupply: Double,
    @Json(name = "total_supply") val totalSupply: Double?,
    @Json(name = "max_supply") val maxSupply : Double?,
    val ath: Double,
    @Json(name = "ath_change_percentage") val athChangePercentage: Double,
    @Json(name = "ath_date") val athDate: String,
    val atl: Double,
    @Json(name = "atl_change_percentage") val atlChangePercentage: Double,
    @Json(name = "atl_date") val atlDate: String,
    val roi: ROI?,
    @Json(name = "last_updated") val lastUpdated: String
)

@JsonClass(generateAdapter = true)
data class ROI(
    val times: Double,
    val currency: String,
    val percentage: Double
)
