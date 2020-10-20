package com.example.afop.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.dataSource.RegisterDataSource
import com.example.afop.data.model.Result
import com.example.afop.ui.auth.register.RegisterResult
import com.example.afop.util.IO

class RegisterRepository(private val dataSource: RegisterDataSource) {
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