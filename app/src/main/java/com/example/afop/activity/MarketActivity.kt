package com.example.afop.activity

import android.os.Bundle
import com.example.afop.R
import com.example.afop.ui.main.market.marketSale.MarketSaleFragment

/**
 * 마켓 관련 액티비티
 */
class MarketActivity : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)

        val marketData = intent.getSerializableExtra("data")

        if(marketData == null) {
            switchFragment(MarketSaleFragment.newInstance())
        }

        if (savedInstanceState == null) {

        }
    }
}