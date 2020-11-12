package com.example.afop.data.repository

import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.response.Result
import com.example.afop.ui.auth.login.LoginResult
import com.example.afop.ui.auth.register.RegisterResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * 로그인 관련 레포지토리
 */
class LoginRepository(private val dataSource: DataSource) {
    fun login(email: String, password: String, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            dataSource.login(
                mapOf(
                    "email" to email,
                    "password" to password
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