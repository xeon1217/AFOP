package com.example.afop.ui.main.market.marketSell

data class MarketSellState (
    val titleError: Int? = null,
    val priceError: Int? = null,
    val contentError: Int? = null,
    val isMarketDataValid: Boolean = false
)