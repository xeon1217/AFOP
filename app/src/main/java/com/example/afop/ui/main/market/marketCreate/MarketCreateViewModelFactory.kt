package com.example.afop.ui.main.market.marketCreate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.repository.MarketRepository

class MarketCreateViewModelFactory : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketCreateViewModel::class.java)) {
            return MarketCreateViewModel(
                repository = MarketRepository(
                    dataSource = DataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}