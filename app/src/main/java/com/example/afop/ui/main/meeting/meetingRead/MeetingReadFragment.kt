package com.example.afop.ui.main.meeting.meetingRead

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.databinding.FragmentMeetingCreateBinding
import com.example.afop.databinding.FragmentMeetingReadBinding
import com.example.afop.ui.main.community.CommunityFragment
import com.example.afop.ui.main.home.MainHomeFragment
import com.example.afop.ui.main.market.MarketFragment
import com.example.afop.ui.main.meeting.MeetingFragment
import com.example.afop.ui.main.meeting.meetingCreate.MeetingCreateViewModel
import com.example.afop.ui.main.meeting.meetingCreate.MeetingCreateViewModelFactory
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.util.PreferenceFragment

class MeetingReadFragment : Fragment() {

    private lateinit var binding: FragmentMeetingReadBinding
    private lateinit var viewModel: MeetingReadViewModel
    private lateinit var mActivity: ActivityExtendFunction

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting_create, container, false)
        viewModel = ViewModelProvider(viewModelStore, MeetingReadViewModelFactory()).get(MeetingReadViewModel::class.java)
        arguments?.getString(ActivityExtendFunction.ActivityType.UPDATE.name)?.let {
            //아이템 가져오기
        }
        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {

    }

    /*
    fun initBottomNavigationView() {
        binding.meetingBottomNavigationView.visibility = View.VISIBLE
        binding.meetingBottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (binding.meetingBottomNavigationView.selectedItemId != item.itemId) {
                when (item.itemId) {
                    R.id.menuItemHome -> {
                        switchFragment(MainHomeFragment.newInstance())
                    } // 홈
                    R.id.menuItemMeeting -> {
                        switchFragment(MeetingFragment.newInstance())
                    } // 모임
                    R.id.menuItemMarket -> {
                        switchFragment(MarketFragment.newInstance())
                    } // 중고마켓
                    R.id.menuItemCommunity -> {
                        switchFragment(CommunityFragment.newInstance())
                    } // 커뮤니티
                    R.id.menuItemPreferences -> {
                        switchFragment(PreferenceFragment.newInstance())
                    }
                }
            }
            true
        }
    }

     */

    companion object {
        fun newInstance() =
            MarketFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}