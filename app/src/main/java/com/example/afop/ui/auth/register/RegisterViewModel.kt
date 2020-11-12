package com.example.afop.ui.auth.register

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.util.UiViewModel
import com.example.afop.data.repository.RegisterRepository
import com.example.afop.data.response.Result

class RegisterViewModel(private val repository: RegisterRepository) : UiViewModel() {
    private val _registerState = MutableLiveData<RegisterState>()
    val state: LiveData<RegisterState> = _registerState

    private var _result = MutableLiveData<Result<*>>()
    val result: LiveData<Result<*>> = _result


    fun verifyEmail(email: String) {
        repository.verifyEmail(email) { result ->
            _result.postValue(result)
        }
    }

    fun register(email: String, name: String, password: String, verifyPassword: String, nickName: String) {

    }

    fun registerStateChanged(email: String, name: String, password: String, verifyPassword: String, nickName: String, state: View) {
        if (isEmailValid(email) != null) {
            _registerState.value = RegisterState(emailError = isEmailValid(email))
        } else if (isEmailValid(email) == null && state.visibility != View.GONE) {
            _registerState.value = RegisterState(isEmailDataValid = true)
        } else if (isNameValid(name) != null) {
            _registerState.value = RegisterState(nameError = isNameValid(name))
        } else if (isPasswordValid(password) != null) {
            _registerState.value = RegisterState(passwordError = isPasswordValid(password))
        } else if (isVerifyPasswordValid(password, verifyPassword) != null) {
            _registerState.value = RegisterState(verifyPasswordError = isVerifyPasswordValid(password, verifyPassword))
        } else if (isNickNameValid(nickName) != null) {
            _registerState.value = RegisterState(nickNameError = isNickNameValid(nickName))
        } else {
            _registerState.value = RegisterState(isFormDataValid = true)
        }
    }
}