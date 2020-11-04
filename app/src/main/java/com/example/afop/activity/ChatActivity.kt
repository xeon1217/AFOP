package com.example.afop.activity

import android.os.Bundle
import com.example.afop.R
import com.example.afop.ui.main.chat.ChatFragment

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