package com.example.afop.ui.main.market

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afop.R
import com.example.afop.activity.MainActivity
import com.example.afop.activity.MyActivity
import kotlinx.android.synthetic.main.fragment_market.*

class MarketFragment : Fragment() {
    private lateinit var mActivity: MyActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_market, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as MyActivity
        mActivity.changeTitle(R.string.title_market)
    }

    companion object {
        fun newInstance() =
            MarketFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}