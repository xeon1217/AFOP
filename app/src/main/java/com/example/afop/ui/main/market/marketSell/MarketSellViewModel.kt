package com.example.afop.ui.main.market.marketSell

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketVO
import com.example.afop.data.model.Result
import com.example.afop.data.model.UiViewModel
import com.example.afop.data.repository.MarketRepository

class MarketSellViewModel (private val repository: MarketRepository) : UiViewModel() {
    private var _result = MutableLiveData<Result<MarketSellResult>>()
    val result: LiveData<Result<MarketSellResult>> = _result

    fun sale(content: MarketVO) {
        repository.sale(content) { result ->
            _result.value = result
        }
    }
}