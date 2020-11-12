package com.example.afop.data.repository

import android.util.Log
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.MarketDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * 마켓 관련 레포지토리
 */
class MarketRepository(private val dataSource: DataSource) {
    fun sell(_item: MarketDTO, callback: (Result<MarketDTO>) -> Unit) {
        /*
        CoroutineScope(IO).launch {
            try {
                dataSource.marketPutItem(_item)
                callback(Result(data = null, result = MarketResult(isSuccessPutItem = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }

         */
    }

    fun getList(callback: (Result<List<MarketDTO>>) -> Unit) {
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

    fun getItem(_marketID: String, callback: (Result<MarketDTO>) -> Unit) {
        /*
        CoroutineScope(IO).launch {
            try {
                var item = MarketDTO()
                dataSource.marketGetItem(_marketID).let { _document ->
                    _document.toObject(MarketDTO::class.java)?.let { _item ->
                        _item.marketID = _document.id
                        item = _item
                    }
                }
                callback(Result(data = item, result = MarketResult(isSuccessGetItem = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }

         */
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
        return "dataSource.getUID()"
    }
}