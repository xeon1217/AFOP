package com.example.afop.data.repository

import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.model.Result
import com.example.afop.ui.main.market.marketSell.MarketSellResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * 마켓 관련 레포지토리
 */
class MarketRepository(private val dataSource: DataSource) {
    fun sell(item: MarketDTO, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            try {
                dataSource.marketPutItem(item)
                callback(Result(data = null, result = MarketSellResult(isSuccess = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }
    }
}