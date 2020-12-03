package com.example.afop.ui.main.meeting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
    private var isOpen: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting, container, false)
        binding.fragment = this
        mActivity = activity as ActivityExtendFunction
        viewModel = ViewModelProvider(viewModelStore, MeetingViewModelFactory()).get(MeetingViewModel::class.java)
        subscribeUi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as ActivityExtendFunction
        mActivity.changeTitle(R.string.title_menu_meeting)
        initFAB()
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

    fun openCreate(view: View) {
        hideFAB()
        mActivity.startActivity(Intent(context, MeetingActivity::class.java).putExtra(ActivityExtendFunction.ActivityType.CREATE.name, ""))
    }

    private fun initFAB() {
        val fabOpen = AnimationUtils.loadAnimation(mActivity, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(mActivity, R.anim.fab_close)

        binding.openMenuFloatingButton.setOnClickListener {
            if (!isOpen) {
                showFAB()
            } else {
                hideFAB()
            }
        }
    }

    private fun hideFAB() {
        isOpen = false

        binding.apply {
            invalidateAll()

            createFloatingButton.hide()

            val fabRClockwise = AnimationUtils.loadAnimation(mActivity, R.anim.rotate_clock_wise)
            openMenuFloatingButton.startAnimation(fabRClockwise)
        }
    }

    private fun showFAB() {
        isOpen = true

        binding.apply {
            invalidateAll()

            createFloatingButton.show()

            val fabRAntiClockwise = AnimationUtils.loadAnimation(mActivity, R.anim.rotate_anti_clock_wise)
            openMenuFloatingButton.startAnimation(fabRAntiClockwise)
        }
    }

    fun isOpen(): Boolean {
        return isOpen
    }

    companion object {
        fun newInstance() =
            MeetingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}