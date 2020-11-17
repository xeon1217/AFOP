package com.example.afop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.data.model.MarketDTO
import com.example.afop.ui.main.market.marketDetail.MarketDetailFragment
import com.example.afop.ui.main.market.marketDetail.MarketDetailViewModel
import com.example.afop.ui.main.market.marketDetail.MarketDetailViewModelFactory
import com.example.afop.ui.main.market.marketSell.MarketSellFragment
import com.example.afop.util.ActivityExtendFunction
import kotlinx.android.synthetic.main.activity_market.*

/**
 * 마켓 관련 액티비티
 */
class MarketActivity : ActivityExtendFunction() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)
        if (savedInstanceState == null) { }

        switchFragment(MarketSellFragment.newInstance())

        intent.getLongExtra("detail", -1).let {
            if(it != -1L) {
                switchFragment(MarketDetailFragment.newInstance(), bundle = bundleOf("detail" to it))
            }
        }

        intent.getLongExtra("modify", -1).let {
            if(it != -1L) {
                switchFragment(MarketSellFragment.newInstance(), bundle = bundleOf("modify" to it))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}