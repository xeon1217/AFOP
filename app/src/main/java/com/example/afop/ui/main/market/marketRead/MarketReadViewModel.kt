package com.example.afop.ui.main.market.marketRead

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.repository.MarketRepository
import com.example.afop.util.CustomViewModel
import com.example.afop.data.response.Result

class MarketReadViewModel(private val repository: MarketRepository) : CustomViewModel() {
    private var _result = MutableLiveData<Result<MarketDTO>>()
    val result: LiveData<Result<MarketDTO>> = _result

    var item: MarketDTO = MarketDTO()

    fun getUID(): String {
        return repository.getUID()
    }

    fun getItem(marketID: String) {
        repository.getItem(marketID) { result ->
            result.data?.let {
                item = it
            }
            _result.postValue(result.copy(response = MarketReadResponse(isSuccessGetItem = result.response?.success)))
        }
    }

    fun modify(item: MarketDTO) {
        repository.modify(item) { result ->
            result.data?.let {
                this.item = it
            }
            _result.postValue(result.copy(response = MarketReadResponse(isSuccessModifyItem = result.response?.success)))
        }
    }
}