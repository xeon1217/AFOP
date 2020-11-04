package com.example.afop.ui.main.market.marketList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afop.R
import com.example.afop.activity.LoginActivity
import com.example.afop.activity.MarketActivity
import com.example.afop.activity.MyActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_market.*
import kotlinx.android.synthetic.main.fragment_market_list.*

/**
 * 마켓의 글을 읽어오는데 사용
 * 전체 글, 내가 판매한 글, 내가 판매 중인 글, 내가 구매한 글
 */
class MarketListFragment : Fragment() {
    private lateinit var mActivity: MyActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_market_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //변수 초기화
        mActivity = activity as MyActivity
    }

    companion object {
        fun newInstance() =
            MarketListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}