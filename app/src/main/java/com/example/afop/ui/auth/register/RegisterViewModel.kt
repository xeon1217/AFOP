package com.example.afop.ui.auth.register

import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.afop.R
import com.example.afop.data.model.Result
import com.example.afop.data.model.UiViewModel
import com.example.afop.data.model.User
import com.example.afop.data.repository.RegisterRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterViewModel(private val repository: RegisterRepository) : UiViewModel() {
    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState

    private var _result = MutableLiveData<Result<*>>()
    val result: LiveData<Result<*>> = _result

    fun checkEmail(email: String) {
        repository.checkEmail(email) { result ->
            _result.value = result
        }
    }

    fun checkNickName(nickName: String) {
        repository.checkNickName(nickName) { result ->
            _result.value = result
        }
    }

    fun register(email: String, name: String, password: String, verifyPassword: String, nickName: String) {
        repository.register(
            email = email,
            name = name,
            password = password,
            verifyPassword = verifyPassword,
            nickName = nickName
        ) { result ->
            _result.value = result
        }
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