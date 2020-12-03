package com.example.afop.ui.main.market.marketCreate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketDTO
import com.example.afop.util.CustomViewModel
import com.example.afop.data.repository.MarketRepository
import com.example.afop.data.response.Result

class MarketCreateViewModel (private val repository: MarketRepository) : CustomViewModel() {
    private var _sellState = MutableLiveData<MarketCreateState>()
    val state: LiveData<MarketCreateState> = _sellState

    private var _result = MutableLiveData<Result<*>>()
    val result: LiveData<Result<*>> = _result

    var item: MarketDTO = MarketDTO()

    fun sell(item: MarketDTO) {
        repository.sell(item) { result ->
            _result.postValue(result.copy(response = MarketCreateResponse(isSuccessPutItem = result.response?.success)))
        }
    }

    fun modify(item: MarketDTO) {
        repository.modify(item) { result ->
            _result.postValue(result.copy(response = MarketCreateResponse(isSuccessModifyItem = result.response?.success)))
        }
    }

    fun getItem(marketID: String) {
        repository.getItem(marketID) { result ->
            result.data?.let {
                item = it
            }
            _result.postValue(result.copy(response = MarketCreateResponse(isSuccessGetItem = result.response?.success)))
        }
    }

    fun sellStateChanged(title: String, price: String, content: String) {
        if(isEmptyValid(title) != null) {
            _sellState.value = MarketCreateState(titleError = isEmptyValid(title))
        } else if (isEmptyValid(price) != null) {
            _sellState.value = MarketCreateState(priceError = isEmptyValid(price))
        } else if (price.length > 16) { // 오류부분 설계할 것
            _sellState.value = MarketCreateState(priceError = isEmptyValid(price))
        } else if (isEmptyValid(content) != null) {
            _sellState.value = MarketCreateState(contentError = isEmptyValid(content))
        } else {
            _sellState.value = MarketCreateState(isMarketDataValid = true)
        }
    }
}