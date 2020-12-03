package com.example.afop.ui.main.meeting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.afop.R
import com.example.afop.data.dataSource.DataSource
import com.example.afop.databinding.FragmentMeetingBinding
import com.example.afop.ui.activity.MarketActivity
import com.example.afop.ui.activity.MeetingActivity
import com.example.afop.ui.adapter.MeetingListAdapter
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.util.Util

class MeetingFragment : Fragment() {
    private lateinit var binding: FragmentMeetingBinding
    private lateinit var mActivity: ActivityExtendFunction
    private lateinit var viewModel: MeetingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting, container, false)
        mActivity = activity as ActivityExtendFunction
        viewModel = ViewModelProvider(viewModelStore, MeetingViewModelFactory()).get(MeetingViewModel::class.java)
        subscribeUi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as ActivityExtendFunction
        mActivity.changeTitle(R.string.title_menu_meeting)
    }

    private fun subscribeUi() {
        val meetingListAdapter = MeetingListAdapter(context)
        binding.apply {
            viewModel = viewModel
            recyclerView.adapter = meetingListAdapter
            recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (!v.canScrollVertically(1)) {

                }
            }
            refreshLayout.setOnRefreshListener {

            }
        }
    }

    fun createMeeting(view: View) {
        mActivity.startActivity(Intent(context, MeetingActivity::class.java).putExtra(ActivityExtendFunction.ActivityType.CREATE.name, ""))
    }

    companion object {
        fun newInstance() =
            MeetingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}