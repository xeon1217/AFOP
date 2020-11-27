package com.example.afop.ui.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.afop.R
import com.example.afop.data.dataSource.DataSource
import com.example.afop.databinding.FragmentMainMarketBinding
import com.example.afop.databinding.FragmentMarketSellBinding
import com.example.afop.ui.activity.ChatActivity
import com.example.afop.ui.activity.MarketActivity
import com.example.afop.util.ActivityExtendFunction
import kotlinx.android.synthetic.main.fragment_main_market.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class MainMarketFragment : Fragment() {
    private lateinit var mActivity: ActivityExtendFunction
    private lateinit var binding: FragmentMainMarketBinding
    private var isOpen: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_market, container, false)
        binding.fragment = this
        mActivity = activity as ActivityExtendFunction
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as ActivityExtendFunction
        mActivity.changeTitle(getString(R.string.title_menu_market))
        initFAB()
    }

    fun isOpen(): Boolean {
        return isOpen
    }

    fun openSell(view: View) {
        hideFAB()
        mActivity.startActivity(Intent(context, MarketActivity::class.java))
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

        mainMarketOpenMenuFloatingButton.setOnClickListener {
            if (!isOpen) {
                showFAB()
            } else {
                hideFAB()
            }
        }
    }

    private fun hideFAB() {
        isOpen = false
        binding.invalidateAll()

        mainMarketFavoriteFloatingButton.hide()
        mainMarketKeywordFloatingButton.hide()
        mainMarketChattingFloatingButton.hide()
        mainMarketSellHistoryFloatingButton.hide()
        mainMarketBuyHistoryFloatingButton.hide()
        mainMarketSellFloatingButton.hide()

        val fabRClockwise = AnimationUtils.loadAnimation(mActivity, R.anim.rotate_clock_wise)
        mainMarketOpenMenuFloatingButton.startAnimation(fabRClockwise)
    }

    private fun showFAB() {
        isOpen = true
        binding.invalidateAll()

        mainMarketSellFloatingButton.show()
        mainMarketBuyHistoryFloatingButton.show()
        mainMarketSellHistoryFloatingButton.show()
        mainMarketChattingFloatingButton.show()
        mainMarketKeywordFloatingButton.show()
        mainMarketFavoriteFloatingButton.show()

        val fabRAntiClockwise =
            AnimationUtils.loadAnimation(mActivity, R.anim.rotate_anti_clock_wise)
        mainMarketOpenMenuFloatingButton.startAnimation(fabRAntiClockwise)
    }

    companion object {
        fun newInstance() =
            MainMarketFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}