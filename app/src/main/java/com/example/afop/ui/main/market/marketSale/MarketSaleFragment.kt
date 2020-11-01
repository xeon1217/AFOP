package com.example.afop.ui.main.market.marketSale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.activity.MyActivity
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.MarketVO
import com.example.afop.data.model.User
import com.example.afop.ui.auth.login.LoginViewModel
import com.example.afop.ui.auth.login.LoginViewModelFactory
import kotlinx.android.synthetic.main.fragment_market_sale.*

class MarketSaleFragment: Fragment() {
    private lateinit var viewModel: MarketSaleViewModel
    private lateinit var mActivity: MyActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_market_sale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //변수 초기화
        mActivity = activity as MyActivity
        viewModel = ViewModelProvider(viewModelStore, MarketSaleViewModelFactory()).get(MarketSaleViewModel::class.java)

        marketSaleSellButton.setOnClickListener {
            viewModel.sale(
                MarketVO(
                    title = marketSaleTitleEditText.text.toString(),
                    content = marketSaleContentTextView.text.toString(),
                    price = marketSalePriceEditText.text.toString()
                )
            )
            mActivity.finish()
        }
    }

    companion object {
        fun newInstance() =
            MarketSaleFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}