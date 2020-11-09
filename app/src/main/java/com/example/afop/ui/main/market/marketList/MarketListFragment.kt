package com.example.afop.ui.main.market.marketList

import android.os.Bundle
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
        home()
        subscribeUi()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    private fun subscribeUi() {
        val marketListAdapter = MarketListAdapter(context)
        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            if (result == null) {
                return@Observer
            }
            result.apply {
                mActivity.hideLoading()
                result.apply {
                    marketListAdapter.submitList(data)
                    binding.marketRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
                    binding.marketRecyclerView.adapter = marketListAdapter
                }
                error?.let {
                    AlertDialog.Builder(mActivity).apply {
                        setCancelable(false)
                        setTitle(getString(R.string.dialog_title_fail))
                        setMessage("글을 읽어오는데 실패했습니다!")
                        setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                        }
                    }.show()
                }
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