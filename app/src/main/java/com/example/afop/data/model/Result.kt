package com.example.afop.data.model

/**
 * 서버와 통신하는 등의 콜백에 사용 될 데이터 모델
 */
data class Result<T> (
        val data: T? = null,
        val state: T? = null,
        val error: Exception? = null
)