package com.example.afop.ui.main.market.marketList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.afop.R
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.data.Adapter.MarketListAdapter
import com.example.afop.databinding.FragmentMarketListBinding
import kotlinx.android.synthetic.main.fragment_market_list.*

/**
 * 마켓의 글을 읽어오는데 사용
 * 전체 글, 내가 판매한 글, 내가 판매 중인 글, 내가 구매한 글
 */
class MarketListFragment : Fragment() {
    private lateinit var binding: FragmentMarketListBinding
    private lateinit var viewModel: MarketListViewModel
    private lateinit var mActivity: ActivityExtendFunction

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_list, container, false)
        viewModel = ViewModelProvider(viewModelStore, MarketListViewModelFactory()).get(MarketListViewModel::class.java)
        mActivity = activity as ActivityExtendFunction
        subscribeUi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        marketRefreshLayout.setOnRefreshListener {
            home()
            marketRefreshLayout.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        home()
    }

    private fun subscribeUi() {
        val marketListAdapter = MarketListAdapter(context)
        binding.marketRecyclerView.adapter = marketListAdapter
        binding.marketRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        binding.marketRecyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(!v.canScrollVertically(1)) {
                Log.d("List","asdadsdadd")
            }
        }
        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            if (result == null) {
                return@Observer
            }
        })
    }

    private fun home() {
        mActivity.showLoading()
        viewModel.getList()
    }

    companion object {
        fun newInstance() =
            MarketListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}