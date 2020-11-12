package com.example.afop.data.repository

import android.util.Log
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.response.Response
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.afop.data.response.Result
import com.example.afop.ui.auth.register.RegisterResponse

/**
 * 회원가입 관련 레포지토리
 */
class RegisterRepository(private val dataSource: DataSource) {
    fun verifyEmail(email: String, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            dataSource.verifyEmail(email).apply {
                response?.let {
                    callback(Result(data = data, response = RegisterResponse(isVerifyEmail = true)))
                }
                error?.let {
                    callback(Result(data = data, error = error))
                }
            }
        }
    }

    fun verifyNickname(nickname: String, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            dataSource.verifyNickname(nickname).apply {
                response?.let {
                    callback(Result(data = data, response = RegisterResponse(isVerifyNickname = true)))
                }
                error?.let {
                    callback(Result(data = data, error = error))
                }
            }
        }
    }

    fun register(
        email: String,
        name: String,
        password: String,
        verifyPassword: String,
        nickName: String,
        callback: (Result<*>) -> Unit
    ) {
        CoroutineScope(IO).launch {
            dataSource.register(
                mapOf(
                    "email" to email,
                    "rPassword" to password,
                    "vPassword" to verifyPassword,
                    "name" to name,
                    "nickName" to nickName
                )
            ).apply {
                response?.let {
                    callback(Result(data = data, response = RegisterResponse(isRegister = true)))
                }
                error?.let {
                    callback(Result(data = data, error = error))
                }
            }
        }
    }
}