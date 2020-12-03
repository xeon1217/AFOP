package com.example.afop.ui.main.market.marketCreate

data class MarketCreateState (
    val titleError: Int? = null,
    val priceError: Int? = null,
    val contentError: Int? = null,
    val imageError: Int? = null,
    val isMarketDataValid: Boolean = false
)