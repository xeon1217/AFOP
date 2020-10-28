package com.example.afop.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.afop.data.model.Result
import com.example.afop.data.model.UiViewModel
import com.example.afop.data.repository.LoginRepository

class LoginViewModel(private val repository: LoginRepository) : UiViewModel() {
    private val _loginPrompt = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> = _loginPrompt

    private var _result = MutableLiveData<Result<LoginResult>>()
    val result: LiveData<Result<LoginResult>> = _result

    fun login(email: String, password: String) {
        repository.login(email = email, password = password) { result ->
            _result.value = result
        }
    }

    fun autoLogin() {
        repository.autoLogin(){ result ->
            _result.value = result
        }
    }

    fun setAutoLogin(value: Boolean) {
        repository.setAutoLogin(value)
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