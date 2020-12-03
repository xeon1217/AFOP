package com.example.afop.ui.main.meeting.meetingCreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.databinding.FragmentMeetingCreateBinding
import com.example.afop.util.ActivityExtendFunction

class MeetingCreateFragment : Fragment() {
    private lateinit var binding: FragmentMeetingCreateBinding
    private lateinit var viewModel: MeetingCreateViewModel
    private lateinit var mActivity: ActivityExtendFunction

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting_create, container, false)
        viewModel = ViewModelProvider(viewModelStore, MeetingCreateViewModelFactory()).get(MeetingCreateViewModel::class.java)
        arguments?.getString(ActivityExtendFunction.ActivityType.UPDATE.name)?.let {
            //아이템 가져오기
        }
        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {

    }

    fun create() {

    }

    fun modify() {

    }

    fun exit() {

    }
}