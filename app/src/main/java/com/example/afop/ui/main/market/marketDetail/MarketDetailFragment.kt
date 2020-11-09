package com.example.afop.ui.main.market.marketDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.data.model.MarketDTO
import com.example.afop.databinding.FragmentMarketDetailBinding
import com.example.afop.databinding.FragmentMarketListBinding
import com.example.afop.ui.activity.ChatActivity
import com.example.afop.ui.activity.MainActivity
import com.example.afop.ui.activity.MarketActivity
import com.example.afop.ui.main.market.marketList.MarketListFragment
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

    private fun subscribeUi() {
        binding.viewModel = viewModel
        binding.fragment = this
        binding.item = arguments?.get("item") as MarketDTO
        //mActivity.initToolbar(binding.marketDetailToolbar)
    }

    fun modify(view: View) {
        mActivity.startActivity(Intent(context, MarketActivity::class.java).putExtra("modify", binding.item))
    }

    fun buy(view: View) {
        mActivity.startActivity(Intent(context, ChatActivity::class.java).putExtra("buy", binding.item))
    }

    companion object {
        fun newInstance() = MarketDetailFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}