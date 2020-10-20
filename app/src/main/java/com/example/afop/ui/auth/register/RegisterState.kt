package com.example.afop.ui.auth.register

data class RegisterState(
    val emailError: Int? = null,
    val isEmailDataValid: Boolean = false,

    val nameError: Int? = null,
    val passwordError: Int? = null,
    val verifyPasswordError: Int? = null,
    val nickNameError: Int? = null,
    val isFormDataValid: Boolean = false
)