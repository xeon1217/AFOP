package com.example.afop.ui.main.market

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.afop.R
import com.example.afop.data.dataSource.DataSource
import com.example.afop.databinding.FragmentMarketBinding
import com.example.afop.ui.activity.MarketActivity
import com.example.afop.ui.adapter.MarketListAdapter
import com.example.afop.util.ActivityExtendFunction

class MarketFragment : Fragment() {
    private lateinit var binding: FragmentMarketBinding
    private lateinit var mActivity: ActivityExtendFunction
    private lateinit var viewModel: MarketViewModel
    private var isOpen: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market, container, false)
        binding.fragment = this
        mActivity = activity as ActivityExtendFunction
        viewModel = ViewModelProvider(viewModelStore, MarketViewModelFactory()).get(MarketViewModel::class.java)
        subscribeUi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as ActivityExtendFunction
        mActivity.changeTitle(getString(R.string.title_menu_market))
        initFAB()
    }

    override fun onResume() {
        super.onResume()
        getList()
    }

    private fun subscribeUi() {
        val marketListAdapter = MarketListAdapter(context)
        binding.apply {
            viewModel = viewModel
            marketRecyclerView.adapter = marketListAdapter
            marketRecyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            marketRecyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (!v.canScrollVertically(1)) {
                    getListInfinity()
                }
            }
            marketRefreshLayout.setOnRefreshListener {
                getList()
            }
        }

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            if (result == null) {
                return@Observer
            }

            mActivity.hideLoading()
            AlertDialog.Builder(mActivity).apply {
                setCancelable(false)
                result.response?.let { response ->
                    (response as MarketResponse).apply {
                        binding.marketRefreshLayout.isRefreshing = false
                        isSuccessGetList?.let { success ->
                            if (success) {
                                marketListAdapter.submitList(result.data)
                                marketListAdapter.notifyDataSetChanged()
                            } else {
                                Toast.makeText(mActivity, "더 이상 불러올 데이터가 없습니다!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            return@Observer
                        }
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

    private fun getList(last_id_cursor: Long? = null) {
        mActivity.showLoading()
        viewModel.getList(last_id_cursor = last_id_cursor)
    }

    private fun getListInfinity() {
        viewModel.marketList.let { marketList ->
            if (marketList.size > 0) {
                marketList[marketList.size - 1]?.let { last_item ->
                    getList(last_id_cursor = last_item.seq)
                }
            }
        }
    }

    fun isOpen(): Boolean {
        return isOpen
    }

    fun openSell(view: View) {
        hideFAB()
        mActivity.startActivity(Intent(context, MarketActivity::class.java).putExtra(ActivityExtendFunction.ActivityType.CREATE.name, ""))
    }

    fun openBuyHistory(view: View) {
        hideFAB()
        Toast.makeText(mActivity, getString(R.string.alert_not_implement_function), Toast.LENGTH_SHORT).show()
        mActivity.startActivity(Intent(context, MarketActivity::class.java).putExtra("sell", DataSource.getUser().uid))
    }

    fun openSellHistory(view: View) {
        hideFAB()
        Toast.makeText(mActivity, getString(R.string.alert_not_implement_function), Toast.LENGTH_SHORT).show()
        mActivity.startActivity(Intent(context, MarketActivity::class.java).putExtra("buy", DataSource.getUser().uid))
    }

    fun openChatting(view: View) {
        hideFAB()
        Toast.makeText(mActivity, getString(R.string.alert_not_implement_function), Toast.LENGTH_SHORT).show()
        //mActivity.startActivity(Intent(context, ChatActivity::class.java))
    }

    fun openKeyword(view: View) {
        hideFAB()
        Toast.makeText(mActivity, getString(R.string.alert_not_implement_function), Toast.LENGTH_SHORT).show()
        //mActivity.startActivity(Intent(context, MarketActivity::class.java))
    }

    fun openFavorite(view: View) {
        hideFAB()
        Toast.makeText(mActivity, getString(R.string.alert_not_implement_function), Toast.LENGTH_SHORT).show()
        //mActivity.startActivity(Intent(context, MarketActivity::class.java))
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

            favoriteFloatingButton.hide()
            keywordFloatingButton.hide()
            chatFloatingButton.hide()
            sellHistoryFloatingButton.hide()
            buyHistoryFloatingButton.hide()
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
            buyHistoryFloatingButton.show()
            sellHistoryFloatingButton.show()
            chatFloatingButton.show()
            keywordFloatingButton.show()
            favoriteFloatingButton.show()

            val fabRAntiClockwise = AnimationUtils.loadAnimation(mActivity, R.anim.rotate_anti_clock_wise)
            openMenuFloatingButton.startAnimation(fabRAntiClockwise)
        }
    }

    companion object {
        fun newInstance() =
            MarketFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}