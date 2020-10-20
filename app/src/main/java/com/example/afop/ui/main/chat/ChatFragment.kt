package com.example.afop.ui.main.chat

import android.os.Bundle
import com.example.afop.ui.auth.register.RegisterFragment

class ChatFragment {
    companion object {
        @JvmStatic
        fun newInstance() =
                RegisterFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}