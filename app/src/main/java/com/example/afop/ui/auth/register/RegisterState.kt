package com.example.afop.ui.auth.register

data class EmailData(
    val emailError: Int? = null,
    val isDataValid: Boolean = false
)

data class FormData(
    val nameError: Int? = null,
    val passwordError: Int? = null,
    val verifyPasswordError: Int? = null,
    val nickNameError: Int? = null,
    val isDataValid: Boolean = false
)