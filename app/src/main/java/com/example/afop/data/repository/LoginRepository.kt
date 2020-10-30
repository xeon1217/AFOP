package com.example.afop.data.repository

import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.Result
import com.example.afop.ui.auth.login.LoginResult

/**
 * 로그인 관련 레포지토리
 */
class LoginRepository(private val dataSource: DataSource) {
    fun login(email: String, password: String, callback: (Result<LoginResult>) -> Unit) {
        dataSource.login(email = email, password = password) { result ->
            callback(result)
        }
    }

    fun autoLogin(callback: (Result<LoginResult>) -> Unit) {
        dataSource.autoLogin() { result ->
            callback(result)
        }
    }

    fun setAutoLogin(value: Boolean) {
        dataSource.setAutoLogin(value)
    }
}