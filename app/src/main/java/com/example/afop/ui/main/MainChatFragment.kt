package com.example.afop.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afop.R
import com.example.afop.util.ActivityExtendFunction

class MainChatFragment : Fragment() {
    private lateinit var mActivity: ActivityExtendFunction
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as ActivityExtendFunction
        //mActivity.changeTitle() 이 곳에 채팅방 이름
    }

    companion object {
        fun newInstance() =
            MainChatFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}