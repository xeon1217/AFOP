package com.example.afop.ui.main.market.marketSell

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketDTO
import com.example.afop.util.UiViewModel
import com.example.afop.data.repository.MarketRepository
import com.example.afop.data.response.Result

class MarketSellViewModel (private val repository: MarketRepository) : UiViewModel() {
    private var _sellState = MutableLiveData<MarketSellState>()
    val state: LiveData<MarketSellState> = _sellState

    private var _result = MutableLiveData<Result<*>>()
    val result: LiveData<Result<*>> = _result

    fun sell(item: MarketDTO) {
        repository.sell(item) { result ->
            _result.postValue(result.copy(response = MarketSellResponse(isSuccessPutItem = result.response?.success)))
        }
    }

    fun modify(item: MarketDTO) {
        repository.modify(item) { result ->
            _result.postValue(result.copy(response = MarketSellResponse(isSuccessModifyItem = result.response?.success)))
        }
    }

    fun getItem(marketID: Long) {
        repository.getItem(marketID) { result ->
            _result.postValue(result.copy(response = MarketSellResponse(isSuccessGetItem = result.response?.success)))
        }
    }

    fun sellStateChanged(title: String, price: String, content: String) {
        if(isEmptyValid(title) != null) {
            _sellState.value = MarketSellState(titleError = isEmptyValid(title))
        } else if (isEmptyValid(price) != null) {
            _sellState.value = MarketSellState(priceError = isEmptyValid(price))
        } else if (price.length > 16) { // 오류부분 설계할 것
            _sellState.value = MarketSellState(priceError = isEmptyValid(price))
        } else if (isEmptyValid(content) != null) {
            _sellState.value = MarketSellState(contentError = isEmptyValid(content))
        } else {
            _sellState.value = MarketSellState(isMarketDataValid = true)
        }
    }
}