package com.example.afop.data.repository

import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.Result

class RegisterRepository(private val dataSource: DataSource) {
    fun checkEmail(email: String, callback: (Result<*>) -> Unit) {
        dataSource.checkEmail(email) { result ->
            callback(result)
        }
    }

    fun checkNickName(nickName: String, callback: (Result<*>) -> Unit) {
        dataSource.checkNickName(nickName) { result ->
            callback(result)
        }
    }

    fun register(email: String, name: String, password: String, verifyPassword: String, nickName: String, callback: (Result<*>) -> Unit) {
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