package com.example.afop.ui.main.meeting.meetingCreate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.repository.MeetingRepository
import com.example.afop.ui.main.market.marketCreate.MarketCreateState
import com.example.afop.util.CustomViewModel

class MeetingCreateViewModel(private val repository: MeetingRepository) : CustomViewModel() {
    private var _promptState = MutableLiveData<MeetingCreateState>()
    val promptState: LiveData<MeetingCreateState> = _promptState

    fun promptChanged(title: String, content: String, member: String) {
        if(isEmptyValid(title) != null) {
            _promptState.value = MeetingCreateState(titleError = isEmptyValid(title))
        } else if (isEmptyValid(member) != null) {
            _promptState.value = MeetingCreateState(memberError = isEmptyValid(member))
        } else if (isEmptyValid(content) != null) {
            _promptState.value = MeetingCreateState(contentError = isEmptyValid(content))
        } else {
            _promptState.value = MeetingCreateState(isMeetingDataValid = true)
        }
    }
}