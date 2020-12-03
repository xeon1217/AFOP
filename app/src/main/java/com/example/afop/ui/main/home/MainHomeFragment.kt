package com.example.afop.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afop.R
import com.example.afop.util.ActivityExtendFunction

class MainHomeFragment : Fragment() {
    private lateinit var mActivity: ActivityExtendFunction
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as ActivityExtendFunction
        mActivity.changeTitle(R.string.app_name)
    }

    companion object {
        fun newInstance() =
            MainHomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}