package com.example.afop.ui.auth.register

import com.example.afop.data.model.ResultModel

data class RegisterResult (
    val isCheckEmail: Boolean? = null,
    val isCheckNickName: Boolean? = null,
    val isRegister: Boolean? = null
) : ResultModel()