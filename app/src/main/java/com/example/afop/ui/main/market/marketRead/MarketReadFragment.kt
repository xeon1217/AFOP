package com.example.afop.ui.main.market.marketRead

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.ui.adapter.ViewPagerAdapter
import com.example.afop.data.model.MarketDTO
import com.example.afop.databinding.FragmentMarketReadBinding
import com.example.afop.ui.activity.MarketActivity
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.util.Util
import com.google.android.material.tabs.TabLayoutMediator

class MarketReadFragment : Fragment() {
    private lateinit var binding: FragmentMarketReadBinding
    private lateinit var viewModel: MarketReadViewModel
    private lateinit var mActivity: ActivityExtendFunction

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_read, container, false)
        viewModel = ViewModelProvider(viewModelStore, MarketReadViewModelFactory()).get(MarketReadViewModel::class.java)
        mActivity = activity as ActivityExtendFunction
        mActivity.initToolbar(binding.toolbar)
        mActivity.showLoading()
        subscribeUi()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        arguments?.getString(ActivityExtendFunction.ActivityType.READ.name)?.let {
            viewModel.getItem(it)
        }
    }

    private fun subscribeUi() {
        val viewPagerAdapter = ViewPagerAdapter(context)
        binding.marketDetailViewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.marketDetailTabLayout, binding.marketDetailViewPager) { tab, position ->
            binding.marketDetailViewPager.currentItem = tab.position
        }.attach()
        binding.viewModel = viewModel
        binding.fragment = this

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            if (result == null) {
                return@Observer
            }
            mActivity.hideLoading()
            AlertDialog.Builder(mActivity).apply {
                setCancelable(false)
                result.response?.let { response ->
                    (response as MarketReadResponse).apply {
                        isSuccessGetItem?.let { success ->
                            binding.marketDetailStateSpinner.setSelection(viewModel.item.state)
                            viewPagerAdapter.submitList(viewModel.item.images)
                            viewPagerAdapter.notifyDataSetChanged()
                        }
                        isSuccessModifyItem?.let { success ->
                        }
                        binding.invalidateAll()
                        return@Observer
                    }
                }
                result.error?.apply {
                    setTitle(getString(R.string.dialog_title_fail))
                    setMessage(message)
                    when {
                        else -> {
                            setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                //mActivity.finish()
                            }
                        }
                    }
                }
            }.show()
        })
    }

    fun modify(view: View) {
        arguments?.getString(ActivityExtendFunction.ActivityType.READ.name)?.let {
            mActivity.startActivity(Intent(context, MarketActivity::class.java).putExtra(ActivityExtendFunction.ActivityType.UPDATE.name, it))
        }
    }

    fun buy(view: View) {
        //mActivity.startActivity(Intent(context, ChatActivity::class.java).putExtra("buy", binding.item))
    }

    val stateListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position) {
                0 -> {
                    viewModel.modify(item = viewModel.item.copy(state = MarketDTO.State.SOLD.ordinal))
                }
                1 -> {
                    viewModel.modify(item = viewModel.item.copy(state = MarketDTO.State.RESERVATION.ordinal))
                }
                2 -> {
                    viewModel.modify(item = viewModel.item.copy(state = MarketDTO.State.SOLD_OUT.ordinal))
                }
            }
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }

    companion object {
        fun newInstance() = MarketReadFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}