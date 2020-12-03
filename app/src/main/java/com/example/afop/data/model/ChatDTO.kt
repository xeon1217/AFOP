package com.example.afop.data.model

/**
 * 채팅에 사용 될 데이터 모델
 */
data class ChatDTO (
    val user: String,
    val message: String,
    val timeStamp: Long
)