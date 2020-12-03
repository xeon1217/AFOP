package com.example.afop.ui.activity

import android.os.Bundle
import com.example.afop.R
import com.example.afop.ui.main.chat.MainChatFragment
import com.example.afop.util.ActivityExtendFunction

/***
 * 채팅 관련 액티비티
 */
class  ChatActivity : ActivityExtendFunction() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        if (savedInstanceState == null) {
            switchFragment(MainChatFragment.newInstance())
        }
    }
}