package com.example.afop.ui.main.market.marketDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.data.Adapter.MarketListAdapter
import com.example.afop.data.Adapter.ViewPagerAdapter
import com.example.afop.data.model.MarketDTO
import com.example.afop.databinding.FragmentMarketDetailBinding
import com.example.afop.ui.activity.MainActivity
import com.example.afop.ui.activity.MarketActivity
import com.example.afop.ui.main.market.marketList.MarketListFragment
import com.example.afop.ui.main.market.marketList.MarketListResponse
import com.example.afop.ui.main.market.marketList.MarketListViewModel
import com.example.afop.util.ActivityExtendFunction

class MarketDetailFragment : Fragment() {
    private lateinit var binding: FragmentMarketDetailBinding
    private lateinit var viewModel: MarketDetailViewModel
    private lateinit var mActivity: ActivityExtendFunction

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_detail, container, false)
        viewModel = ViewModelProvider(viewModelStore, MarketDetailViewModelFactory()).get(MarketDetailViewModel::class.java)
        mActivity = activity as ActivityExtendFunction
        subscribeUi()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        arguments?.getLong("detail")?.let {
            viewModel.getItem(it)
        }
    }

    private fun subscribeUi() {
        val viewPagerAdapter = ViewPagerAdapter(context)
        binding.marketDetailViewPager.adapter = viewPagerAdapter
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
                    (response as MarketDetailResponse).apply {
                        isSuccessGetItem?.let { success ->
                            binding.item = result.data
                            binding.marketDetailStateSpinner.setSelection(binding.item!!.state)
                            viewPagerAdapter.submitList(binding.item!!.images)
                            viewPagerAdapter.notifyDataSetChanged()
                        }
                        isSuccessModifyItem?.let { success ->
                            binding.item = result.data
                        }
                        return@Observer
                    }
                }
                result.error?.apply {
                    setTitle(getString(R.string.dialog_title_fail))
                    setMessage(message)
                    when (this) {
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
        arguments?.getLong("detail")?.let {
            mActivity.startActivity(Intent(context, MarketActivity::class.java).putExtra("modify", it))
        }
    }

    fun buy(view: View) {
        //mActivity.startActivity(Intent(context, ChatActivity::class.java).putExtra("buy", binding.item))
    }

    val stateListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position) {
                0 -> {
                    viewModel.modify(item = binding.item!!.copy(state = MarketDTO.State.SOLD.ordinal))
                }
                1 -> {
                    viewModel.modify(item = binding.item!!.copy(state = MarketDTO.State.RESERVATION.ordinal))
                }
                2 -> {
                    viewModel.modify(item = binding.item!!.copy(state = MarketDTO.State.SOLD_OUT.ordinal))
                }
            }
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }


    companion object {
        fun newInstance() = MarketDetailFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}