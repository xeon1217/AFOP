package com.example.afop.ui.main.market

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.afop.R
import com.example.afop.activity.MarketActivity
import com.example.afop.activity.MyActivity
import com.example.afop.ui.main.market.marketList.MarketListFragment
import com.example.afop.ui.main.market.marketSale.MarketSaleFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_market.*

class MarketFragment: Fragment() {
    private lateinit var mActivity: MyActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_market, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //변수 초기화
        mActivity = activity as MyActivity
        mActivity.changeTitle(R.string.title_menu_market)

        marketFloatingActionButton.setOnClickListener {
            mActivity.startActivity(Intent(mActivity, MarketActivity::class.java))
        }

        marketViewPager.adapter = MarketViewPagerAdapter(this)
        TabLayoutMediator(marketTabLayout, marketViewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.title_menu_market_list)
                1 -> tab.text = getString(R.string.title_menu_market_my_sale)
                2 -> tab.text = getString(R.string.title_menu_market_chat)
            }
        }.attach()
        marketTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {}//mActivity.changeTitle(R.string.title_account_find_email)
                    1 -> {}// mActivity.changeTitle(R.string.title_account_find_password)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    inner class MarketViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> MarketListFragment.newInstance()
                1 -> MarketListFragment.newInstance()
                2 -> MarketListFragment.newInstance()
                else -> MarketListFragment.newInstance()
            }
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