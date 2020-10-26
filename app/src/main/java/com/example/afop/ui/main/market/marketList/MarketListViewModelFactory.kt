package com.example.afop.ui.main.market.marketList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.repository.MarketRepository

class MarketListViewModelFactory : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MarketListViewModel::class.java)) {
        return MarketListViewModel(
            repository = MarketRepository(
                dataSource = DataSource()
            )
        ) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
}
}