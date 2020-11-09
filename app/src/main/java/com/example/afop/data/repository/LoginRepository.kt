package com.example.afop.data.repository

import android.util.Log
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.Result
import com.example.afop.ui.auth.login.LoginResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * 로그인 관련 레포지토리
 */
class LoginRepository(private val dataSource: DataSource) {
    fun login(email: String, password: String, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            try {
                dataSource.manualLogin(email, password).let { user ->
                    dataSource.isEmailVerified(user)
                    dataSource.getUser(user.uid)
                    dataSource.refreshLocalFCMToken().let { token ->
                        dataSource.refreshRemoteFCMToken(user, token)
                    }
                }
                callback(Result(data = null, result = LoginResult(isLogin = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }
    }

    fun autoLogin(callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            try {
                dataSource.autoLogin().let { user ->
                    dataSource.isEmailVerified(user)
                    dataSource.getUser(user.uid)
                    dataSource.refreshLocalFCMToken().let { token ->
                        dataSource.refreshRemoteFCMToken(user, token)
                    }
                }
                callback(Result(data = null, result = LoginResult(isLogin = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }
    }

    fun setAutoLogin(value: Boolean) {
        dataSource.setAutoLogin(value)
    }
}