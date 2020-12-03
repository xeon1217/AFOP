package com.example.afop.ui.main.meeting.meetingCreate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.repository.MarketRepository
import com.example.afop.data.repository.MeetingRepository

class MeetingCreateViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeetingCreateViewModel::class.java)) {
            return MeetingCreateViewModel(
                repository = MeetingRepository(
                    dataSource = DataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}