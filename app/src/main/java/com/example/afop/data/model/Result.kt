package com.example.afop.data.model

/**
 * 서버와 통신하는 등의 콜백에 사용 될 데이터 모델
 */
data class Result<T> (
        var data: T? = null,
        var result: ResultModel? = null,
        var error: Exception? = null
)