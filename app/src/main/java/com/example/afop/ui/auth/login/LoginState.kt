package com.example.afop.ui.auth.login

data class LoginState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)