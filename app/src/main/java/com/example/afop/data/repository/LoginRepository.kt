package com.example.afop.data.repository

import com.example.afop.data.dataSource.LoginDataSource
import com.example.afop.data.model.Result

class LoginRepository(private val dataSource: LoginDataSource) {
    fun login(email: String, password: String, callback: (Result<*>) -> Unit) {
        dataSource.login(email = email, password = password) { result ->
            callback(result)
        }
    }
}