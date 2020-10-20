package com.example.afop.ui.auth.register

data class RegisterResult (
    val isCheckEmail: Boolean? = null,
    val isCheckNickName: Boolean? = null,
    val isRegister: Boolean? = null,
    val error: Int? = null
)