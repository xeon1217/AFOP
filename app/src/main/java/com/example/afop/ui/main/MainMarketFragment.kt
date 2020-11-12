package com.example.afop.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afop.R
import com.example.afop.ui.activity.MarketActivity
import com.example.afop.util.ActivityExtendFunction
import kotlinx.android.synthetic.main.fragment_main_market.*

class MainMarketFragment : Fragment() {
    private lateinit var mActivity: ActivityExtendFunction
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main_market, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as ActivityExtendFunction
        mActivity.changeTitle(getString(R.string.title_menu_market))

        floatingActionButton.setOnClickListener {
            mActivity.startActivity(Intent(context, MarketActivity::class.java).putExtra("sell", "sell"))
        }
    }

    companion object {
        fun newInstance() =
            MainMarketFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}