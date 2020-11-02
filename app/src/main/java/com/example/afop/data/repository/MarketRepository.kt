package com.example.afop.data.repository

import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.MarketVO
import com.example.afop.data.model.Result
import com.example.afop.ui.main.market.marketSell.MarketSellResult

/**
 * 마켓 관련 레포지토리
 */
class MarketRepository(private val dataSource: DataSource) {
    fun sale(content: MarketVO, callback: (Result<MarketSellResult>) -> Unit) {
    }
}