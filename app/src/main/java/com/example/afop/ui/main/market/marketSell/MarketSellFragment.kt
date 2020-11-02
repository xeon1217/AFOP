package com.example.afop.ui.main.market.marketSell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.activity.MyActivity
import com.example.afop.data.model.MarketVO
import kotlinx.android.synthetic.main.fragment_market_sell.*

class MarketSellFragment: Fragment() {
    private lateinit var viewModel: MarketSellViewModel
    private lateinit var mActivity: MyActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_market_sell, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //변수 초기화
        mActivity = activity as MyActivity
        viewModel = ViewModelProvider(viewModelStore, MarketSellViewModelFactory()).get(MarketSellViewModel::class.java)

        marketSellButton.setOnClickListener {
            viewModel.sale(
                MarketVO(
                    title = marketSellTitleTextInputEditText.text.toString(),
                    price = marketSellPriceTextInputEditText.text.toString(),
                    content = marketSellContentTextInputEditText.text.toString()
                )
            )
            mActivity.finish()
        }
    }

    companion object {
        fun newInstance() =
            MarketSellFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}