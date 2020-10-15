package com.example.afop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afop.R
import com.example.afop.data.model.User
import com.example.afop.ui.auth.login.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        User.auth = FirebaseAuth.getInstance()
        //User.databaseReference = FirebaseDatabase.getInstance()

        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }
}