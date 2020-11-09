package com.example.afop.ui.activity

import android.os.Bundle
import com.example.afop.R
import com.example.afop.ui.auth.ResetPassword.ResetPasswordFragment
import com.example.afop.util.ActivityExtendFunction

/**
 * 패스워드 초기화 관련 액티비티
 */
class ResetPasswordActivity : ActivityExtendFunction() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        if (savedInstanceState == null) {
            switchFragment(ResetPasswordFragment.newInstance())
        }
    }
}