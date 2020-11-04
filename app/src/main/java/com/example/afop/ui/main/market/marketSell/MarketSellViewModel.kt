package com.example.afop.ui.main.market.marketSell

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.model.Result
import com.example.afop.data.model.UiViewModel
import com.example.afop.data.repository.MarketRepository

class MarketSellViewModel (private val repository: MarketRepository) : UiViewModel() {
    private var _sellState = MutableLiveData<MarketSellState>()
    val state: LiveData<MarketSellState> = _sellState

    private var _result = MutableLiveData<Result<*>>()
    val result: LiveData<Result<*>> = _result

    fun sell(item: MarketDTO) {
        repository.sell(item) {result ->
            _result.postValue(result)
        }
    }

    fun sellStateChanged(title: String, price: String, content: String) {
        if(isEmptyValid(title) != null) {
            _sellState.value = MarketSellState(titleError = isEmptyValid(title))
        } else if (isEmptyValid(price) != null) {
            _sellState.value = MarketSellState(priceError = isEmptyValid(price))
        } else if (isEmptyValid(content) != null) {
            _sellState.value = MarketSellState(contentError = isEmptyValid(content))
        } else {
            _sellState.value = MarketSellState(isMarketDataValid = true)
        }
    }
}