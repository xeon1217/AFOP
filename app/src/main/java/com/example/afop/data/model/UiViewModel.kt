package com.example.afop.data.model

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.afop.R
import java.util.regex.Pattern
private const val MIN_PASSWORD_LENGTH = 8

open class UiViewModel : ViewModel() {

    //비어있지 않은가?
    fun isValid(str: String): Boolean {
        return str.isNotBlank()
    }

    //문자열에 특수문자가 포함되어 있는가?
    fun isCharacterValid(str: String) {
    }

    //문자열이 비어있지 않은가?
    fun isEmptyValid(str: String): Int? {
        return if(str.isNotBlank()) {
            R.string.error_invalid_empty
        } else {
            null
        }
    }

    //이메일 양식인가?
    fun isEmailValid(email: String): Int? {
        return if (email.isBlank()) { // 입력이 비어 있는가?
            R.string.error_invalid_empty
        } else if (email.contains(" ")) {
            R.string.error_invalid_blank
        } else if (!email.contains("@").and(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            R.string.error_invalid_email
        } else {
            null
        }
    }

    fun isNameValid(name: String): Int? {
        return if (name.isBlank()) {
            R.string.error_invalid_empty
        } else if (name.contains(" ")) {
            R.string.error_invalid_blank
        } else {
            null
        }
    }

    fun isPasswordValid(password: String): Int? {
        return if (password.isBlank()) {
            R.string.error_invalid_empty
        } else if (password.contains(" ")) {
            R.string.error_invalid_blank
        } else if(password.length < MIN_PASSWORD_LENGTH) {
            R.string.error_invalid_password_length
        } else {
            null
        }
    }

    fun isVerifyPasswordValid(password: String, verifyPassword: String): Int? {
        return if(verifyPassword.isBlank()) {
            R.string.error_invalid_empty
        } else if (verifyPassword.contains(" ")) {
            R.string.error_invalid_blank
        } else if (password != verifyPassword) {
            R.string.error_invalid_password_not_equal
        } else {
            null
        }
    }

    fun isNickNameValid(nickName: String): Int? {
        return if(nickName.isBlank()) {
            R.string.error_invalid_empty
        } else if (nickName.contains(" ")) {
            R.string.error_invalid_blank
        } else {
            null
        }
    }
}