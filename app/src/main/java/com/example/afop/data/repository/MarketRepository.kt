package com.example.afop.data.repository

import android.util.Log
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.response.Response
import com.example.afop.ui.auth.register.RegisterResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import com.example.afop.data.response.Result
import com.example.afop.ui.main.market.marketDetail.MarketDetailResponse
import com.example.afop.ui.main.market.marketList.MarketListResponse
import com.example.afop.ui.main.market.marketSell.MarketSellResponse
import java.util.*
import kotlin.collections.ArrayList

/**
 * 마켓 관련 레포지토리
 */
class MarketRepository(private val dataSource: DataSource) {
    fun sell(item: MarketDTO, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            item.timeStamp = Date().time
            item.sellerUID = DataSource.getUser().uid
            dataSource.marketPutItem(item = item).apply {
                response?.let { response ->
                    callback(Result(data = data, response = response))
                }
                error?.let { error ->
                    callback(Result(data = data, error = error))
                }
            }
        }
    }

    fun getList(title: String? = null, last_id_cursor: Long?, callback: (Result<ArrayList<MarketDTO?>>) -> Unit) {
        CoroutineScope(IO).launch {
            dataSource.marketGetList(title = title, last_id_cursor = last_id_cursor).apply {
                response?.let { response ->
                    callback(Result(data = data, response = response))
                }
                error?.let { error ->
                    callback(Result(data = data, error = error))
                }
            }
        }
        /*
        CoroutineScope(IO).launch {
            try {
                val items: ArrayList<MarketDTO> = ArrayList()

                /*
                //위치 관련
                val location = dataSource.getLocationRoot()
                location.documents.forEach { root ->
                    Log.d("Location", "시/도 - ${root.id}")
                    root.data?.forEach {
                        Log.d("Location", "군/구 - ${it.key}")
                        (it.value as Map<*, *>).forEach { last ->
                            Log.d("Location", "읍/면/동 - ${last.key}")
                        }
                    }
                }
                 */

                dataSource.marketGetList().documents.forEach { _document ->
                    _document.toObject(MarketDTO::class.java)?.let { _item ->
                        _item.marketID = _document.id
                        items.add(_item)
                    }
                }
                callback(Result(data = items, result = MarketResult(isSuccessGetItem = true)))
            } catch (e: Exception) {
                Log.d("asd", e.toString())
                callback(Result(data = null, error = e))
            }
        }

         */
    }

    fun getItem(marketID: Long, callback: (Result<MarketDTO>) -> Unit) {
        CoroutineScope(IO).launch {
            dataSource.marketGetItem(marketID).apply {
                response?.let { response ->
                    callback(Result(data = data, response = response))
                }
                error?.let { error ->
                    callback(Result(data = data, error = error))
                }
            }
        }
    }

    fun modify(_item: MarketDTO, callback: (Result<MarketDTO>) -> Unit) {
        /*
        CoroutineScope(IO).launch {
            try {
                dataSource.marketModifyItem(_item)
                callback(Result(data = null, result = MarketResult(isSuccessPutItem = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }

         */
    }

    fun getUID(): String {
        return DataSource.getUser().uid
    }
}