package com.example.afop.ui.main.market.marketList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
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
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.response.ErrorCode
import com.example.afop.databinding.FragmentMarketListBinding
import com.example.afop.ui.auth.register.RegisterResponse
import kotlinx.android.synthetic.main.fragment_market_list.*
import kotlin.math.log

/**
 * 마켓의 글을 읽어오는데 사용
 * 전체 글, 내가 판매한 글, 내가 판매 중인 글, 내가 구매한 글
 */
class MarketListFragment : Fragment() {
    private lateinit var binding: FragmentMarketListBinding
    private lateinit var mActivity: ActivityExtendFunction

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_list, container, false)
        binding.viewModel = ViewModelProvider(viewModelStore, MarketListViewModelFactory()).get(MarketListViewModel::class.java)
        mActivity = activity as ActivityExtendFunction
        subscribeUi()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getList()
    }

    private fun subscribeUi() {
        val marketListAdapter = MarketListAdapter(context)
        binding.marketRecyclerView.adapter = marketListAdapter
        binding.marketRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        binding.marketRecyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(!v.canScrollVertically(1)) {
                getListInfinity()
            }
        }
        binding.marketRefreshLayout.setOnRefreshListener {
            getList()
            marketRefreshLayout.isRefreshing = false
        }

        binding.viewModel!!.result.observe(viewLifecycleOwner, Observer { result ->
            if (result == null) {
                return@Observer
            }
            mActivity.hideLoading()
            AlertDialog.Builder(mActivity).apply {
                setCancelable(false)
                result.response?.let { response ->
                    (response as MarketListResponse).apply {
                        isSuccessGetList?.let { success ->
                            if (success) {
                                marketListAdapter.submitList(result.data)
                                marketListAdapter.notifyDataSetChanged()
                            } else {
                                Toast.makeText(mActivity, "더 이상 불러올 데이터가 없습니다!", Toast.LENGTH_SHORT).show()
                            }
                            return@Observer
                        }
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

    private fun getList(last_id_cursor: Long? = null) {
        mActivity.showLoading()
        binding.viewModel!!.getList(last_id_cursor = last_id_cursor)
    }

    private fun getListInfinity() {
        binding.viewModel?.marketList?.let { marketList ->
            if(marketList.size > 0) {
                marketList[marketList.size - 1]?.let { last_item ->
                    getList(last_id_cursor = last_item.marketID)
                }
            }
        }
    }

    companion object {
        fun newInstance() =
            MarketListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}