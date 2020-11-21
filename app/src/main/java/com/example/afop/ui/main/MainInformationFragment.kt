package com.example.afop.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afop.R
import com.example.afop.util.ActivityExtendFunction

class MainInformationFragment : Fragment() {
    private lateinit var mActivity: ActivityExtendFunction
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as ActivityExtendFunction
        mActivity.changeTitle(R.string.title_menu_meeting)
    }

    companion object {
        fun newInstance() =
            MainInformationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}