package com.example.afop.ui.main.meeting.meetingCreate

data class MeetingCreateState (
    val titleError: Int? = null,
    val contentError: Int? = null,
    val memberError: Int? = null,
    val isMeetingDataValid: Boolean = false
)