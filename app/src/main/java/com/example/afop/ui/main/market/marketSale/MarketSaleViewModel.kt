package com.example.afop.ui.main.market.marketSale

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketVO
import com.example.afop.data.model.Result
import com.example.afop.data.model.UiViewModel
import com.example.afop.data.repository.MarketRepository
import com.example.afop.ui.auth.login.LoginResult
import com.example.afop.ui.auth.register.RegisterResult

class MarketSaleViewModel (private val repository: MarketRepository) : UiViewModel() {
    private var _result = MutableLiveData<Result<MarketSaleResult>>()
    val result: LiveData<Result<MarketSaleResult>> = _result

    fun sale(content: MarketVO) {
        repository.sale(content) { result ->
            _result.value = result
        }
    }
}