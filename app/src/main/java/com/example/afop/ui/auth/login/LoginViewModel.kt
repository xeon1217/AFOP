package com.example.afop.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.data.dataSource.DataSource
import com.example.afop.util.UiViewModel
import com.example.afop.data.repository.LoginRepository
import com.example.afop.data.response.Result

class LoginViewModel(private val repository: LoginRepository) : UiViewModel() {
    private val _loginPrompt = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> = _loginPrompt

    private var _result = MutableLiveData<Result<*>>()
    val result: LiveData<Result<*>> = _result

    fun login(email: String, password: String) {
        repository.login(email = email, password = password) { result ->
            _result.postValue(result)
        }
    }

    fun autoLogin() {
        repository.login() { result ->
            _result.postValue(result)
        }
    }

    fun setAutoLogin(value: Boolean) {
        DataSource.setAutoLogin(value)
    }

    fun loginDataChanged(email: String, password: String) {
        if(isEmailValid(email) != null) {
            _loginPrompt.value = LoginState(emailError = isEmailValid(email))
        } else if(isPasswordValid(password) != null) {
            _loginPrompt.value = LoginState(passwordError = isPasswordValid(password))
        } else {
            _loginPrompt.value = LoginState(isDataValid = true)
        }
    }
}