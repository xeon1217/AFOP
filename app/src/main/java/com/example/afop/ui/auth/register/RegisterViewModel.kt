package com.example.afop.ui.auth.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.afop.R
import com.example.afop.data.model.UiViewModel

class RegisterViewModel : UiViewModel() {
    private val _emailPrompt = MutableLiveData<EmailData>()
    val emailState: LiveData<EmailData> = _emailPrompt

    private val _formPrompt = MutableLiveData<FormData>()
    val formState: LiveData<FormData> = _formPrompt

    fun emailDataChanged(email: String) {
        if (isEmailValid(email) != null) {
            _emailPrompt.value = EmailData(emailError = isEmailValid(email))
        } else {
            _emailPrompt.value = EmailData(isDataValid = true)
        }
    }

    fun formDataChanged(name: String, password: String, verifyPassword: String, nickName: String) {
        if(isNameValid(name) != null) {
            _formPrompt.value = FormData(nameError = isNameValid(name))
        } else if (isPasswordValid(password) != null) {
            _formPrompt.value = FormData(passwordError = isPasswordValid(password))
        } else if (isVerifyPasswordValid(password, verifyPassword) != null) {
            _formPrompt.value = FormData(verifyPasswordError = isVerifyPasswordValid(password, verifyPassword))
        } else if (isNickNameValid(nickName) != null) {
            _formPrompt.value = FormData(nickNameError = isNickNameValid(nickName))
        } else {
            _formPrompt.value = FormData(isDataValid = true)
        }
    }
}