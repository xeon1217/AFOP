package com.example.afop.ui.main.market.marketList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.model.Result
import com.example.afop.util.UiViewModel
import com.example.afop.data.repository.MarketRepository

class MarketListViewModel (private val repository: MarketRepository) : UiViewModel() {
    private var _result = MutableLiveData<Result<List<MarketDTO>>>()
    val result: LiveData<Result<List<MarketDTO>>> = _result

    fun getList() {
        repository.getList { result ->
            _result.postValue(result)
        }
    }
}