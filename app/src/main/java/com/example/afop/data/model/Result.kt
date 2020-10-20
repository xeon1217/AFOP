package com.example.afop.data.model

data class Result<T> (
        val data: T? = null,
        val state: T? = null,
        val error: Exception? = null
)