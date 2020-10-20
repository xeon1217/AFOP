package com.example.afop.data.dataSource

import android.util.Log
import com.example.afop.data.model.Result
import com.example.afop.ui.auth.login.LoginResult
import com.example.afop.util.IO

class LoginDataSource {
    private val dbRef = IO.db.collection("Users")

    fun login(email: String, password: String, callback: (Result<*>) -> Unit) {
        try {
            IO.auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                dbRef.whereEqualTo("UID", "${IO.auth.uid}").get().addOnSuccessListener { documents ->
                    for(documnet in documents) {
                        Log.d("LoginData", "$documnet")
                    }
                    callback(Result(state = LoginResult(isLogin = true)))
                }
            }.addOnFailureListener {
                callback(Result(state = LoginResult(isLogin = false), error = it))
            }
        } catch (e: Exception) {
            callback(Result(state = LoginResult(isLogin = false), error = e))
        }
    }
}

/*
mActivity.enableBlock()
        User.auth.signInWithEmailAndPassword(loginEmailTextInputEditText.text.toString(), loginPasswordTextInputEditText.text.toString()).addOnCompleteListener { task ->
            mActivity.disableBlock()
            if(task.isSuccessful) {
                if (User.auth.currentUser?.isEmailVerified!!) {
                } else {
                    AlertDialog.Builder(mActivity).apply {
                        setCancelable(false)
                        setMessage(getString(R.string.dialog_message_need_email_verify))
                        setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                        }
                        show()
                    }
                }
            } else {
                AlertDialog.Builder(mActivity).apply {
                    setCancelable(false)
                    setMessage(getString(R.string.dialog_message_error_login_fail))
                    setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                        loginEmailTextInputEditText.setText("")
                        loginPasswordTextInputEditText.setText("")
                    }
                    show()
                }
            }
        }
        */