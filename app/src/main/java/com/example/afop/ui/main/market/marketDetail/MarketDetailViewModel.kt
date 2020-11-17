package com.example.afop.ui.main.market.marketDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.repository.MarketRepository
import com.example.afop.data.response.Response
import com.example.afop.util.UiViewModel
import com.example.afop.data.response.Result

class MarketDetailViewModel(private val repository: MarketRepository) : UiViewModel() {
    private var _result = MutableLiveData<Result<MarketDTO>>()
    val result: LiveData<Result<MarketDTO>> = _result

    fun getUID(): String {
        return repository.getUID()
    }

    fun getItem(marketID: Long) {
        repository.getItem(marketID) { result ->
            _result.postValue(result.copy(response = MarketDetailResponse(isSuccessGetItem = result.response?.success)))
        }
    }
}