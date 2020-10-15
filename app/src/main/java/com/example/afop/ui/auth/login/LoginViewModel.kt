package com.example.afop.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.afop.data.model.UiViewModel

class LoginViewModel : UiViewModel() {
    private val _loginPrompt = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> = _loginPrompt

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