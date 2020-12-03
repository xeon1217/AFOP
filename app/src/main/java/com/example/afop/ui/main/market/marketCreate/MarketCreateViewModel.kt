package com.example.afop.ui.main.market.marketCreate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketDTO
import com.example.afop.util.CustomViewModel
import com.example.afop.data.repository.MarketRepository
import com.example.afop.data.response.Result

class MarketCreateViewModel (private val repository: MarketRepository) : CustomViewModel() {
    private var _promptState = MutableLiveData<MarketCreateState>()
    val promptState: LiveData<MarketCreateState> = _promptState

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

    fun promptChanged(title: String, price: String, content: String) {
        if(isEmptyValid(title) != null) {
            _promptState.value = MarketCreateState(titleError = isEmptyValid(title))
        } else if (isEmptyValid(price) != null) {
            _promptState.value = MarketCreateState(priceError = isEmptyValid(price))
        } else if (price.length > 16) { // 오류부분 설계할 것
            _promptState.value = MarketCreateState(priceError = isEmptyValid(price))
        } else if (isEmptyValid(content) != null) {
            _promptState.value = MarketCreateState(contentError = isEmptyValid(content))
        } else {
            _promptState.value = MarketCreateState(isMarketDataValid = true)
        }
    }
}