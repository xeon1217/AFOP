package com.example.afop.data.model

data class MarketSummaryDTO (
    val id: String,
    val seq: Long,
    val title: String,
    val price: String,
    val state: MarketDTO.State,
    val category: MarketDTO.Category,
    val image: String
)