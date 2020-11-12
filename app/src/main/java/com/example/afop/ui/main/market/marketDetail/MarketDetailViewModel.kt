package com.example.afop.ui.main.market.marketDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.repository.MarketRepository
import com.example.afop.util.UiViewModel

class MarketDetailViewModel(private val repository: MarketRepository) : UiViewModel() {
    private var _result = MutableLiveData<Result<MarketDTO>>()
    val result: LiveData<Result<MarketDTO>> = _result

    fun getUID(): String {
        return repository.getUID()
    }

    fun getItem(_marketID: String) {
        repository.getItem(_marketID) { result ->
            _result.postValue(result)
        }
    }
}