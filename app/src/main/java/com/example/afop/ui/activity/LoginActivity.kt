package com.example.afop.ui.activity

import android.content.Intent
import android.os.Bundle
import com.example.afop.R
import com.example.afop.data.dataSource.DataSource
import com.example.afop.service.ForcedTerminationService
import com.example.afop.ui.auth.login.LoginFragment
import com.example.afop.util.ActivityExtendFunction

/**
 *  로그인 관련 액티비티
 */
class LoginActivity : ActivityExtendFunction() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            switchFragment(LoginFragment.newInstance())
        }
    }
}