package com.example.afop.ui.main.meeting.meetingCreate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.databinding.FragmentMeetingCreateBinding
import com.example.afop.ui.main.market.MarketFragment
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
            // 업데이트
        }
        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        binding.fragment = this
        binding.viewModel = viewModel
        binding.textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.promptChanged(
                    title = binding.titleTextInputEditText.text.toString(),
                    content = binding.contentTextInputEditText.text.toString(),
                    member = binding.memberTextInputEditText.text.toString(),
                )
            }
        }

        viewModel.promptState.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            state.apply {
                binding.confirmButton.isEnabled = state.isMeetingDataValid
                //경고 관련 주석
                //marketSellTitleTextInputLayout.error = state.titleError?.let { getString(it) }
                //marketSellPriceTextInputLayout.error = state.priceError?.let { getString(it) }
                //marketSellContentTextInputLayout.error = state.contentError?.let { getString(it) }
            }
        })
    }

    fun create() {

    }

    fun modify() {

    }

    fun exit() {

    }

    companion object {
        fun newInstance() =
            MeetingCreateFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}