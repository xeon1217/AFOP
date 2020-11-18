package com.example.afop.ui.main.market.marketSell

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.afop.R
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.data.model.MarketDTO
import com.example.afop.databinding.FragmentMarketSellBinding
import kotlinx.android.synthetic.main.fragment_market_sell.*
import java.io.File

/**
 * 마켓에 판매글을 올릴 때 사용
 */
class MarketSellFragment : Fragment() {
    private lateinit var binding: FragmentMarketSellBinding
    private lateinit var viewModel: MarketSellViewModel
    private lateinit var mActivity: ActivityExtendFunction

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_sell, container, false)
        binding.fragment = this
        binding.item = MarketDTO()
        viewModel = ViewModelProvider(viewModelStore, MarketSellViewModelFactory()).get(MarketSellViewModel::class.java)
        mActivity = activity as ActivityExtendFunction
        subscribeUi()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        arguments?.getLong("modify")?.let { viewModel.getItem(it) }
    }

    private fun subscribeUi() {
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
                    (response as MarketSellResponse).apply {
                        isSuccessPutItem?.let {
                            setTitle(getString(R.string.dialog_title_success))
                            setMessage(getString(R.string.dialog_message_writing_success))
                            setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                mActivity.finish()
                            }.show()
                        }
                        isSuccessGetItem?.let {
                            binding.item = result.data as MarketDTO?
                        }
                        isSuccessModifyItem?.let {
                            setTitle(getString(R.string.dialog_title_success))
                            setMessage(getString(R.string.dialog_message_writing_success))
                            setPositiveButton(getString(R.string.action_confirm)) { _, _ ->
                                mActivity.finish()
                            }.show()
                        }
                    }
                }
                result.error?.apply {
                    setTitle(getString(R.string.dialog_title_fail))
                    setMessage(message)
                    when (this) {
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
            binding.item!!.copy(
                title = marketSellTitleTextInputEditText.text.toString(),
                price = marketSellPriceTextInputEditText.text.toString(),
                content = marketSellContentTextInputEditText.text.toString(),
                negotiation = marketSellNegotiationCheckBox.isChecked,
                category = marketSellCategorySpinner.selectedItem.toString()
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

    fun modify(view: View) {
        mActivity.showLoading()
        viewModel.modify(
            binding.item!!.copy(
                title = marketSellTitleTextInputEditText.text.toString(),
                price = marketSellPriceTextInputEditText.text.toString(),
                content = marketSellContentTextInputEditText.text.toString(),
                negotiation = marketSellNegotiationCheckBox.isChecked,
                category = marketSellCategorySpinner.selectedItem.toString()
            )
        )
    }

    val PICTURE_REQUEST_CODE = 100

    @SuppressLint("IntentReset")
    fun imageAdd(view: View) {
        binding.item?.title = binding.marketSellTitleTextInputEditText.text.toString()
        binding.item?.price = binding.marketSellPriceTextInputEditText.text.toString()
        binding.item?.content = binding.marketSellContentTextInputEditText.text.toString()
        binding.item?.negotiation = binding.marketSellNegotiationCheckBox.isChecked
        binding.item?.category = binding.marketSellCategorySpinner.selectedItemId.toString()

        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        startActivityForResult(Intent.createChooser(intent, "사진을 선택해주세요."), PICTURE_REQUEST_CODE)
    }

    fun imageDelete(view: View, position: Int) {
        binding.item!!.images.removeAt(position)
        binding.invalidateAll()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICTURE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.clipData?.apply {
                        for (i in 0 until itemCount) {
                            if (binding.item!!.images.size < 10) {
                                binding.item!!.images.add(getRealPathFromURI(requireContext(), getItemAt(i).uri))
                            } else {
                                Toast.makeText(context, "이미지는 10장까지 첨부가 가능합니다!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        binding.invalidateAll()
                    }
                }
            }
        }
    }

    private fun getRealPathFromURI(context: Context, uri: Uri): String {
        var columnIndex = 0
        var proj = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = context.contentResolver.query(uri, proj, null, null, null)
        if (cursor?.moveToFirst()!!) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }

    companion object {
        fun newInstance() =
            MarketSellFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}