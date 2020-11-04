package com.example.afop.data.repository

import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.Result
import com.example.afop.ui.auth.register.RegisterResult
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 회원가입 관련 레포지토리
 */
class RegisterRepository(private val dataSource: DataSource) {
    fun checkEmail(email: String, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            try {
                dataSource.checkEmail(email)
                callback(Result(data = null, result = RegisterResult(isCheckEmail = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }
    }

    fun checkNickName(nickName: String, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            try {
                dataSource.checkNickName(nickName)
                callback(Result(data = null, result = RegisterResult(isCheckNickName = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }
    }

    fun register(email: String, name: String, password: String, verifyPassword: String, nickName: String, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            try {
                dataSource.checkEmail(email)
                dataSource.checkNickName(nickName)
                dataSource.register(email, name, password, verifyPassword, nickName)
                callback(Result(data = null, result = RegisterResult(isRegister = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }
    }
}