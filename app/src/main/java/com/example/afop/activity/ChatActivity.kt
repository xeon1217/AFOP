package com.example.afop.activity

import android.os.Bundle
import com.example.afop.R
import com.example.afop.data.model.User
import com.example.afop.ui.auth.login.LoginFragment
import com.example.afop.ui.main.chat.ChatFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/***
 * 채팅 관련 액티비티
 */
class ChatActivity : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_chat)
        if (savedInstanceState == null) {
            switchFragment(ChatFragment.newInstance())
        }
    }
}