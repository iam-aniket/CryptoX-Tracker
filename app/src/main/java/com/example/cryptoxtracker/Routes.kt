package com.example.cryptoxtracker

object Routes {
    var homeScreen = "home_screen"
    var cryptoScreen = "crypto_portfolio"
    var stockScreenIND = "stock_portfolio_IND"
    var stockScreenUSA = "stock_portfolio_USA"
    var otherAssetScreen = "other_portfolio"
    const val coinDetailsScreen = "detail_screen/{cryptoImg}/{cryptoName}/{cryptoSymbol}/{price24h}/{priceChange24h}/{currentPrice}/{quantity}/{percentage}/{cryptoHoldingValue}"
    var detailScreen = "detail_screen/{cryptoImg}/{cryptoName}/{cryptoSymbol}/{price24h}/{priceChange24h}/{currentPrice}/{quantity}/{percentage}/{cryptoHoldingValue}"
}