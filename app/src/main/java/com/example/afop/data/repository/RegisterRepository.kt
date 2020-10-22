package com.example.afop.data.repository

import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.Result
import com.example.afop.ui.auth.register.RegisterResult

class RegisterRepository(private val dataSource: DataSource) {
    fun checkEmail(email: String, callback: (Result<RegisterResult>) -> Unit) {
        dataSource.checkEmail(email) { result ->
            callback(result)
        }
    }

    fun checkNickName(nickName: String, callback: (Result<RegisterResult>) -> Unit) {
        dataSource.checkNickName(nickName) { result ->
            callback(result)
        }
    }

    fun register(email: String, name: String, password: String, verifyPassword: String, nickName: String, callback: (Result<RegisterResult>) -> Unit) {
        dataSource.register(
            email = email,
            name = name,
            password = password,
            verifyPassword = verifyPassword,
            nickName = nickName
        ) { result ->
            callback(result)
        }
    }
}