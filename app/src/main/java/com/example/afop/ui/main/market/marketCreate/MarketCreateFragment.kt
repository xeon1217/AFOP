package com.example.afop.ui.main.market.marketCreate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afop.R
import com.example.afop.databinding.FragmentMarketCreateBinding
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.util.Util
import kotlinx.android.synthetic.main.fragment_market_create.*

/**
 * 마켓에 판매글을 올릴 때 사용
 */
class MarketCreateFragment : Fragment() {
    private lateinit var binding: FragmentMarketCreateBinding
    private lateinit var viewModel: MarketCreateViewModel
    private lateinit var mActivity: ActivityExtendFunction

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_create, container, false)
        viewModel = ViewModelProvider(
            viewModelStore,
            MarketCreateViewModelFactory()
        ).get(MarketCreateViewModel::class.java)
        arguments?.getString(ActivityExtendFunction.ActivityType.UPDATE.name)?.let {
            viewModel.getItem(it)
        }
        mActivity = activity as ActivityExtendFunction
        mActivity.initToolbar(binding.toolbar)
        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        binding.fragment = this
        binding.viewModel = viewModel
        binding.textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.sellStateChanged(
                    title = binding.marketSellTitleTextInputEditText.text.toString(),
                    price = binding.marketSellPriceTextInputEditText.text.toString(),
                    content = binding.marketSellContentTextInputEditText.text.toString()
                )
            }
        }

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            if (state == null) {
                return@Observer
            }
            state.apply {
                binding.marketSellButton.isEnabled = state.isMarketDataValid
                //경고 관련 주석
                //marketSellTitleTextInputLayout.error = state.titleError?.let { getString(it) }
                //marketSellPriceTextInputLayout.error = state.priceError?.let { getString(it) }
                //marketSellContentTextInputLayout.error = state.contentError?.let { getString(it) }
            }
        })

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            if (result == null) {
                return@Observer
            }
            mActivity.hideLoading()
            AlertDialog.Builder(mActivity).apply {
                setCancelable(false)
                result.response?.let { response ->
                    (response as MarketCreateResponse).apply {
                        isSuccessPutItem?.let {
                            setTitle(getString(R.string.dialog_title_success))
                            setMessage(getString(R.string.dialog_message_writing_success))
                            setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                mActivity.finish()
                            }.show()
                        }
                        isSuccessGetItem?.let {
                        }
                        isSuccessModifyItem?.let {
                            setTitle(getString(R.string.dialog_title_success))
                            setMessage(getString(R.string.dialog_message_writing_success))
                            setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                mActivity.finish()
                            }.show()
                        }
                        binding.invalidateAll()
                    }
                }
                result.error?.apply {
                    setTitle(getString(R.string.dialog_title_fail))
                    setMessage(message)
                    when {
                        else -> {
                            setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                mActivity.finish()
                            }
                        }
                    }.show()
                }
            }
        })
    }

    fun sell(view: View) {
        mActivity.showLoading()
        viewModel.sell(
            viewModel.item.copy(
                title = binding.marketSellTitleTextInputEditText.text.toString(),
                price = binding.marketSellPriceTextInputEditText.text.toString(),
                content = binding.marketSellContentTextInputEditText.text.toString(),
                negotiation = binding.marketSellNegotiationCheckBox.isChecked,
                category = binding.marketSellCategorySpinner.selectedItem.toString()
            )
        )
    }

    fun modify(view: View) {
        mActivity.showLoading()
        viewModel.modify(
            viewModel.item.copy(
                title = binding.marketSellTitleTextInputEditText.text.toString(),
                price = binding.marketSellPriceTextInputEditText.text.toString(),
                content = binding.marketSellContentTextInputEditText.text.toString(),
                negotiation = binding.marketSellNegotiationCheckBox.isChecked,
                category = binding.marketSellCategorySpinner.selectedItem.toString()
            )
        )
    }

    fun exit(view: View) {
        AlertDialog.Builder(mActivity).apply {
            setCancelable(false)
            setTitle(getString(R.string.dialog_title_exit))
            setMessage(getString(R.string.dialog_message_market_exit))
            setPositiveButton(getString(R.string.action_exit)) { _, _ ->
                mActivity.finish()
            }
            setNegativeButton(getString(R.string.action_not_exit)) { _, _ ->
            }
        }.show()
    }

    fun saveContent() {
        viewModel.item.title = binding.marketSellTitleTextInputEditText.text.toString()
        viewModel.item.price = binding.marketSellPriceTextInputEditText.text.toString()
        viewModel.item.content = binding.marketSellContentTextInputEditText.text.toString()
        viewModel.item.negotiation = binding.marketSellNegotiationCheckBox.isChecked
        viewModel.item.category = binding.marketSellCategorySpinner.selectedItem.toString()
    }

    val PICTURE_REQUEST_CODE = 100

    fun imageAdd(view: View) {
        //글 내용을 저장
        saveContent()
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(Intent.createChooser(this, "사진을 선택해주세요."), PICTURE_REQUEST_CODE)
        }
    }

    fun imageDelete(view: View, position: Int) {
        viewModel.item.images.removeAt(position)
        binding.invalidateAll()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICTURE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let { intentData ->
                        intentData.data?.apply { // 이미지가 한 개
                            Util.getRealPathFromURI(requireContext(), this)
                                ?.let { viewModel.item.images.add(it) }
                        }
                        intentData.clipData?.apply {  // 이미지가 여러개
                            for (i in 0 until itemCount) {
                                if (viewModel.item.images.size < 10) {
                                    Util.getRealPathFromURI(requireContext(), getItemAt(i).uri)
                                        ?.let { viewModel.item.images.add(it) }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "이미지는 10장까지 첨부가 가능합니다!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        binding.invalidateAll()
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() =
            MarketCreateFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}