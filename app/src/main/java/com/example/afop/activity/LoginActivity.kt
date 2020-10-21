package com.example.afop.activity

import android.os.Bundle
import android.widget.Toast
import com.example.afop.R
import com.example.afop.data.dataSource.DataSource
import com.example.afop.ui.auth.login.LoginFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId

class LoginActivity : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataSource.auth = FirebaseAuth.getInstance()
        DataSource.db = FirebaseFirestore.getInstance()
        DataSource.fcm = FirebaseInstanceId.getInstance()

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new Instance ID token
            val token = task.result?.token

            // Log and toast
            val msg = token
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            switchFragment(LoginFragment.newInstance())
        }
    }
}