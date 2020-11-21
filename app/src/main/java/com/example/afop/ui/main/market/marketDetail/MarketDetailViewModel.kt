package com.example.afop.ui.main.market.marketDetail

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.repository.MarketRepository
import com.example.afop.data.response.Response
import com.example.afop.util.UiViewModel
import com.example.afop.data.response.Result
import com.example.afop.ui.main.market.marketSell.MarketSellResponse

class MarketDetailViewModel(private val repository: MarketRepository) : UiViewModel() {
    private var _result = MutableLiveData<Result<MarketDTO>>()
    val result: LiveData<Result<MarketDTO>> = _result

    var item = MarketDTO()

    fun getUID(): String {
        return repository.getUID()
    }

    fun getItem(marketID: Long) {
        repository.getItem(marketID) { result ->
            result.data?.let {
                item = it
            }
            _result.postValue(result.copy(response = MarketDetailResponse(isSuccessGetItem = result.response?.success)))
        }
    }

    fun modify(item: MarketDTO) {
        repository.modify(item) { result ->
            result.data?.let {
                this.item = it
            }
            _result.postValue(result.copy(response = MarketDetailResponse(isSuccessModifyItem = result.response?.success)))
        }
    }
}