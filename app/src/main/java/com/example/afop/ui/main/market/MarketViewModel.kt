package com.example.afop.ui.main.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketDTO
import com.example.afop.util.CustomViewModel
import com.example.afop.data.repository.MarketRepository
import com.example.afop.data.response.Result

class MarketViewModel(private val repository: MarketRepository) : CustomViewModel() {
    val marketList: ArrayList<MarketDTO?> = ArrayList()

    private var _result = MutableLiveData<Result<ArrayList<MarketDTO?>>>()
    val result: LiveData<Result<ArrayList<MarketDTO?>>> = _result

    fun getList(last_id_cursor: Long?) {
        repository.getList(last_id_cursor = last_id_cursor) { result ->
            if (last_id_cursor == null) {
                marketList.clear()
            }
            if (result.data == null) {
                _result.postValue(result.copy(data = null, error = result.error))
            } else {
                result.data?.let { list ->
                    if (list.size <= 0) {
                        _result.postValue(result.copy(data = marketList, response = MarketResponse(isSuccessGetList = result.response?.success)))
                    } else {
                        marketList.addAll(list)
                        _result.postValue(result.copy(data = marketList, response = MarketResponse(isSuccessGetList = result.response?.success)))
                    }
                }
            }
        }
    }
}