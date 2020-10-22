package com.example.afop.ui.main.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afop.R
import com.example.afop.activity.MyActivity

class MemberFragment : Fragment() {
    private lateinit var mActivity: MyActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_member, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as MyActivity
        //mActivity.changeTitle() 이 곳에 사용자 이름 입력
    }

    companion object {
        fun newInstance() =
            MemberFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}