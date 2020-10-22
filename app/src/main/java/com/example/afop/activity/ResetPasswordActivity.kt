package com.example.afop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afop.R
import com.example.afop.ui.auth.ResetPassword.ResetPasswordFragment
import com.example.afop.ui.auth.login.LoginFragment

class ResetPasswordActivity : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reset_password)
        if (savedInstanceState == null) {
            switchFragment(ResetPasswordFragment.newInstance())
        }
    }
}