package com.example.afop.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.core.os.bundleOf
import com.example.afop.R
import com.example.afop.ui.main.market.marketRead.MarketReadFragment
import com.example.afop.ui.main.market.marketCreate.MarketCreateFragment
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.util.Util
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 마켓 관련 액티비티
 */
class MarketActivity : ActivityExtendFunction() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)
        if (savedInstanceState == null) { }
        initToolbar()

        intent.apply {
            getStringExtra(ActivityType.CREATE.name)?.let {
                switchFragment(MarketCreateFragment.newInstance())
            }

            getStringExtra(ActivityType.READ.name)?.let {
                switchFragment(MarketReadFragment.newInstance(), bundle = bundleOf(ActivityType.READ.name to it))
            }

            getStringExtra(ActivityType.UPDATE.name)?.let {
                switchFragment(MarketCreateFragment.newInstance(), bundle = bundleOf(ActivityType.UPDATE.name to it))
            }

            getStringExtra("sell")?.let {

            }

            getStringExtra("buy")?.let {

            }
        }
        /*
        intent.getLongExtra("modify", -1).let {
            if(it != -1L) {
                switchFragment(MarketCreateFragment.newInstance(), bundle = bundleOf("modify" to it))
            }
        }

        intent.getLongExtra("sell", -1).let { //판매내역
            if(it != -1L) {
                switchFragment(MarketListFragment.newInstance(), bundle = bundleOf("sell" to it))
            }
        }

        intent.getLongExtra("buy", -1).let { //구매내역
            if(it != -1L) {
                switchFragment(MarketListFragment.newInstance(), bundle = bundleOf("buy" to it))
            }
        }
         */
    }

    private fun initToolbar() {
        setSupportActionBar(mainToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.app_name)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}