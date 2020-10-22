package com.example.afop.ui.main.infomation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afop.R
import com.example.afop.activity.MyActivity

class InformationFragment : Fragment() {
    private lateinit var mActivity: MyActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as MyActivity
        mActivity.changeTitle(R.string.title_information)
    }

    companion object {
        fun newInstance() =
            InformationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}