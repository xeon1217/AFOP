package com.example.afop.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afop.R
import com.example.afop.ui.main.market.marketSale.MarketSaleFragment
import kotlinx.android.synthetic.main.fragment_market_sale.*

/**
 * 마켓 관련 액티비티
 */
class MarketActivity : MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)

        val marketData = intent.getSerializableExtra("data")

        if (marketData == null) {
            switchFragment(MarketSaleFragment.newInstance())
        }

        if (savedInstanceState == null) {

        }
    }
}