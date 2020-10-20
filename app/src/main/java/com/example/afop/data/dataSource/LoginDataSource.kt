package com.example.afop.data.dataSource

import android.util.Log
import com.example.afop.R
import com.example.afop.data.model.Result
import com.example.afop.ui.auth.login.LoginResult
import com.example.afop.util.IO

class LoginDataSource {
    private val dbRef = IO.db.collection("Users")

    fun login(email: String, password: String, callback: (Result<LoginResult>) -> Unit) {
        IO.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.exception != null) {
                callback(Result(state = LoginResult(isLogin = false), error = task.exception))
                return@addOnCompleteListener
            }
            task.addOnSuccessListener { auth ->
                if(!auth.user?.isEmailVerified!!) {
                    callback(Result(state = LoginResult(isLogin = false, error = R.string.dialog_message_need_email_verify)))
                    return@addOnSuccessListener
                }
                dbRef.whereEqualTo("UID", auth.user!!.uid).get().addOnCompleteListener { task ->
                    if(task.exception != null) {
                        callback(Result(state = LoginResult(isLogin = false), error = task.exception))
                        return@addOnCompleteListener
                    }
                    callback(Result(state = LoginResult(isLogin = true)))
                }
            }
        }
    }
}
