package com.example.afop.ui.auth.register

import com.example.afop.data.response.Response

data class RegisterResponse (
    val isVerifyEmail: Boolean? = null,
    val isVerifyNickname: Boolean? = null,
    val isRegister: Boolean? = null
): Response()