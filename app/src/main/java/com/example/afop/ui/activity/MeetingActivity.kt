package com.example.afop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.afop.ui.main.meeting.meetingCreate.MeetingCreateFragment
import com.example.afop.ui.main.meeting.meetingRead.MeetingReadFragment
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
                switchFragment(MeetingCreateFragment.newInstance())
            }

            getStringExtra(ActivityType.READ.name)?.let {
                //switchFragment(MeetingReadFragment.newInstance(), bundle = bundleOf(ActivityType.READ.name to it))
            }

            getStringExtra(ActivityType.UPDATE.name)?.let {
                //switchFragment(MarketCreateFragment.newInstance(), bundle = bundleOf(ActivityType.UPDATE.name to it))
            }
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