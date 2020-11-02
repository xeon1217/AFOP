package com.example.afop.data.repository

import android.util.Log
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.Result
import com.example.afop.ui.auth.login.LoginResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 로그인 관련 레포지토리
 */
class LoginRepository(private val dataSource: DataSource) {
    fun login(email: String, password: String, callback: (Result<LoginResult>) -> Unit) {
        CoroutineScope(IO).launch {
            try {
                val user = dataSource.manualLogin(email, password)
                dataSource.isEmailVerified(user)
                val token = dataSource.refreshLocalFCMToken()
                dataSource.refreshRemoteFCMToken(user, token)
                dataSource.getUser(user)
                callback(Result(data = null, result = LoginResult(isLogin = true)))
            } catch (e : Exception) {
                callback(Result(error = e))
            }
        }
    }

    fun autoLogin(callback: (Result<LoginResult>) -> Unit) {
        CoroutineScope(IO).launch {
            try {
                val user = dataSource.autoLogin()
                dataSource.isEmailVerified(user)
                val token = dataSource.refreshLocalFCMToken()
                dataSource.refreshRemoteFCMToken(user, token)
                dataSource.getUser(user)
                callback(Result(data = null, result = LoginResult(isLogin = true)))
            } catch (e : Exception) {
                callback(Result(error = e))
            }
        }
    }

    fun setAutoLogin(value: Boolean) {
        dataSource.setAutoLogin(value)
    }
}