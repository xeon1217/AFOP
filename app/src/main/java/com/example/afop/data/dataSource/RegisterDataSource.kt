package com.example.afop.data.dataSource

import android.util.Log
import com.example.afop.R
import com.example.afop.data.model.Result
import com.example.afop.ui.auth.register.RegisterResult
import com.example.afop.util.IO
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class RegisterDataSource {
    private val dbRef = IO.db.collection("Users")

    fun checkEmail(email: String, callback: (Result<*>) -> Unit) {
        try {
            dbRef.whereEqualTo("Email", email).get().addOnSuccessListener { documents ->
                if(documents.size() == 0) {
                    callback(Result(state = RegisterResult(isCheckEmail = true)))
                } else {
                    callback(Result(state = RegisterResult(isCheckEmail = false)))
                }
            }
        } catch (e: Exception) {
            callback(Result(state = RegisterResult(isCheckEmail = false), error = e))
        }
    }

    fun checkNickName(nickName: String, callback: (Result<*>) -> Unit) {
        try {
            dbRef.whereEqualTo("NickName", nickName).get().addOnSuccessListener { documents ->
                if(documents.size() == 0) {
                    callback(Result(state = RegisterResult(isCheckNickName = true)))
                } else {
                    callback(Result(state = RegisterResult(isCheckNickName = false)))
                }
            }
        } catch (e: Exception) {
            callback(Result(state = RegisterResult(isCheckNickName = false), error = e))
        }
    }

    fun register(email: String, name: String, password: String, verifyPassword: String, nickName: String, callback: (Result<*>) -> Unit) {
        try {
            dbRef.whereEqualTo("NickName", nickName).get().addOnSuccessListener { documents ->
                if(documents.size() == 0) {
                    IO.auth.createUserWithEmailAndPassword(email, password)
                    dbRef.add(
                        hashMapOf(
                            "UID" to IO.auth.uid,
                            "Email" to email,
                            "Name" to name,
                            "NickName" to nickName
                        )
                    )
                    IO.auth.currentUser?.sendEmailVerification()
                    callback(Result(state = RegisterResult(isRegister = true)))
                } else {
                    callback(Result(state = RegisterResult(isCheckNickName = false)))
                }
            }
        } catch (e: Exception) {
            callback(Result(state = RegisterResult(isRegister = false), error = e))
        }
    }
}