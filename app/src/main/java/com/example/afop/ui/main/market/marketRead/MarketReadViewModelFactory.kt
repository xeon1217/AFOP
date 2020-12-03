package com.example.afop.ui.main.market.marketRead

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.repository.MarketRepository

class MarketReadViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketReadViewModel::class.java)) {
            return MarketReadViewModel(
                repository = MarketRepository(
                    dataSource = DataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}