package com.example.afop.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.afop.R
import com.example.afop.data.dataSource.DataSource
import com.example.afop.service.ForcedTerminationService
import com.example.afop.ui.auth.login.LoginFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId

/**
 *  로그인 관련 액티비티
 */
class LoginActivity : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataSource.init(this)
        startService(Intent(this, ForcedTerminationService::class.java))

        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            switchFragment(LoginFragment.newInstance())
        }
    }
}