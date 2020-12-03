package com.example.afop.data.repository

import android.util.Log
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.response.Result
import com.example.afop.ui.auth.login.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * 로그인 관련 레포지토리
 */
class AuthRepository(private val dataSource: DataSource) {
    fun login(loginData: Map<String, String>? = null, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            dataSource.login(loginData).apply {
                response?.let {
                    callback(Result(data = data, response = LoginResponse(isLogin = true)))
                }
                error?.let {
                    callback(Result(data = data, error = error))
                }
            }
        }
    }
}