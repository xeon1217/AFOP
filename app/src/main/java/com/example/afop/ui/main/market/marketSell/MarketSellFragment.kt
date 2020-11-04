package com.example.afop.ui.main.market.marketSell

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.activity.MyActivity
import com.example.afop.data.model.MarketDTO
import com.example.afop.databinding.FragmentMarketSellBinding
import kotlinx.android.synthetic.main.fragment_market_sell.*

/**
 * 마켓에 판매글을 올릴 때 사용
 */
class MarketSellFragment: Fragment() {
    private lateinit var binding: FragmentMarketSellBinding
    private lateinit var viewModel: MarketSellViewModel
    private lateinit var mActivity: MyActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_sell, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //변수 초기화
        mActivity = activity as MyActivity
        viewModel = ViewModelProvider(viewModelStore, MarketSellViewModelFactory()).get(MarketSellViewModel::class.java)
        binding.fragment = this
        binding.textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.sellStateChanged(
                    title = marketSellTitleTextInputEditText.text.toString(),
                    price = marketSellPriceTextInputEditText.text.toString(),
                    content = marketSellContentTextInputEditText.text.toString()
                )
            }
        }

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            state.apply {
                marketSellButton.isEnabled = state.isMarketDataValid
                //marketSellTitleTextInputLayout.error = state.titleError?.let { getString(it) }
                //marketSellPriceTextInputLayout.error = state.priceError?.let { getString(it) }
                //marketSellContentTextInputLayout.error = state.contentError?.let { getString(it) }
            }
        })

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            if (result == null) {
                return@Observer
            }
            result.apply {
                result.result.let {
                    (it as MarketSellResult).let {
                        mActivity.hideLoding()
                        AlertDialog.Builder(mActivity).apply {
                            setCancelable(false)
                            setTitle(getString(R.string.dialog_title_success))
                            setMessage(getString(R.string.dialog_message_writing_success))
                            setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                mActivity.finish()
                            }
                        }.show()
                    }
                }
                error?.let {
                    AlertDialog.Builder(mActivity).apply {
                        setCancelable(false)
                        setTitle(getString(R.string.dialog_title_fail))
                        setMessage(getString(R.string.dialog_message_writing_fail))
                        setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                            mActivity.finish()
                        }
                    }.show()
                }
            }
        })
    }

    fun sell(view: View) {
        mActivity.showLoding()
        viewModel.sell(MarketDTO(
            title = marketSellTitleTextInputEditText.text.toString(),
            price = marketSellPriceTextInputEditText.text.toString(),
            content = marketSellContentTextInputEditText.text.toString(),
            negotiation = marketSellNegotiationCheckBox.isChecked
        ))
    }

    fun exit(view: View) {
        AlertDialog.Builder(mActivity).apply {
            setCancelable(false)
            setTitle(getString(R.string.dialog_title_exit))
            setMessage(getString(R.string.dialog_message_market_exit))
            setPositiveButton(getString(R.string.action_exit)) { _, _ ->
                mActivity.finish()
            }
            setNegativeButton(getString(R.string.action_not_exit)) {_, _->
            }
        }.show()
    }

    companion object {
        fun newInstance() =
            MarketSellFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}