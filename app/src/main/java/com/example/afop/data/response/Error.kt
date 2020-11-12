package com.example.afop.data.response

data class Error(
    val status: Int,
    val code: String,
    val message: String
)