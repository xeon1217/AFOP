package com.example.afop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.example.afop.R
import com.example.afop.databinding.ActivityMeetingBinding
import com.example.afop.ui.main.community.CommunityFragment
import com.example.afop.ui.main.home.MainHomeFragment
import com.example.afop.ui.main.market.MarketFragment
import com.example.afop.ui.main.market.marketCreate.MarketCreateFragment
import com.example.afop.ui.main.market.marketRead.MarketReadFragment
import com.example.afop.ui.main.meeting.MeetingFragment
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.util.PreferenceFragment
import com.example.afop.util.Util

class MeetingActivity : ActivityExtendFunction() {

    private lateinit var binding: ActivityMeetingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meeting)

        if (savedInstanceState == null) {
            //switchFragment(MeetingReadFragment.newInstance())
        }

        intent.apply {
            getStringExtra(ActivityType.CREATE.name)?.let {
                //switchFragment(MarketCreateFragment.newInstance())
            }

            getStringExtra(ActivityType.READ.name)?.let {
                //switchFragment(MarketReadFragment.newInstance(), bundle = bundleOf(ActivityType.READ.name to it))
            }

            getStringExtra(ActivityType.UPDATE.name)?.let {
                //switchFragment(MarketCreateFragment.newInstance(), bundle = bundleOf(ActivityType.UPDATE.name to it))
            }
        }
    }

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

    fun goToLogin(view: View?) {
        startActivityForResult(Intent(this, LoginActivity::class.java), ActivityType.LOGIN.ordinal)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            ActivityType.LOGIN.ordinal -> {
                binding.invalidateAll()
            }
        }
    }
}