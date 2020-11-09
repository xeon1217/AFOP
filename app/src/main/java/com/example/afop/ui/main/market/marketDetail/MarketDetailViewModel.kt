package com.example.afop.ui.main.market.marketDetail

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.model.UserDTO
import com.example.afop.data.repository.MarketRepository
import com.example.afop.util.UiViewModel

class MarketDetailViewModel(private val repository: MarketRepository) : UiViewModel() {
    //lateinit var item: LiveData<MarketDTO>

    fun getUID(): String {
        return repository.getUID()
    }
}