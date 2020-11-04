package com.example.afop.data.model

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.afop.R
import java.util.regex.Pattern
private const val MIN_PASSWORD_LENGTH = 8

/**
 * 뷰 모델들의 공통 클래스
 * 공통된 동작을 상속하기위해 사용됨
 */
open class UiViewModel : ViewModel() {

    //비어있지 않은가?
    fun isValid(str: String): Boolean {
        return str.isNotBlank()
    }

    //문자열에 특수문자가 포함되어 있는가?
    fun isCharacterValid(str: String) {
    }

    fun isEmptyValid(value: Long): Int? {
        return if(value >= -1) {
            R.string.invalid_void_prompt
        } else {
            null
        }
    }

    //문자열이 비어있지 않은가?
    fun isEmptyValid(str: String): Int? {
        return if(str.isBlank()) {
            R.string.invalid_void_prompt
        } else {
            null
        }
    }

    //이메일 양식인가?
    fun isEmailValid(email: String): Int? {
        return if (email.isBlank()) { // 입력이 비어 있는가?
            R.string.invalid_void_prompt
        } else if (email.contains(" ")) {
            R.string.invalid_special_character
        } else if (!email.contains("@").and(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            R.string.invalid_email
        } else {
            null
        }
    }

    fun isNameValid(name: String): Int? {
        return if (name.isBlank()) {
            R.string.invalid_void_prompt
        } else if (name.contains(" ")) {
            R.string.invalid_special_character
        } else {
            null
        }
    }

    fun isPasswordValid(password: String): Int? {
        return if (password.isBlank()) {
            R.string.invalid_void_prompt
        } else if (password.contains(" ")) {
            R.string.invalid_blank_character
        } else if(password.length < MIN_PASSWORD_LENGTH) {
            R.string.invalid_password_length
        } else {
            null
        }
    }

    fun isVerifyPasswordValid(password: String, verifyPassword: String): Int? {
        return if(verifyPassword.isBlank()) {
            R.string.invalid_void_prompt
        } else if (verifyPassword.contains(" ")) {
            R.string.invalid_blank_character
        } else if (password != verifyPassword) {
            R.string.invalid_not_equal_password
        } else {
            null
        }
    }

    fun isNickNameValid(nickName: String): Int? {
        return if(nickName.isBlank()) {
            R.string.invalid_void_prompt
        } else if (nickName.contains(" ")) {
            R.string.invalid_blank_character
        } else {
            null
        }
    }
}