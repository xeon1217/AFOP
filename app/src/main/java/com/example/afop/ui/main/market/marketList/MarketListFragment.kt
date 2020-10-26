package com.example.afop.ui.main.market.marketList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afop.R
import com.example.afop.activity.MyActivity

class MarketListFragment : Fragment() {
    private lateinit var mActivity: MyActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_market_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //변수 초기화
        mActivity = activity as MyActivity
        mActivity.changeTitle(R.string.title_market)
    }

    companion object {
        fun newInstance() =
            MarketListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}