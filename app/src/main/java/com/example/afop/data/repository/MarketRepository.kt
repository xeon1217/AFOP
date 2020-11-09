package com.example.afop.data.repository

import android.util.Log
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.model.Result
import com.example.afop.data.model.UserDTO
import com.example.afop.ui.main.market.marketSell.MarketSellResult
import com.example.afop.util.Util
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * 마켓 관련 레포지토리
 */
class MarketRepository(private val dataSource: DataSource) {
    fun sell(_item: MarketDTO, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            try {
                dataSource.marketPutItem(_item)
                callback(Result(data = null, result = MarketSellResult(isSuccess = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }
    }

    fun getList(callback: (Result<List<MarketDTO>>) -> Unit) {
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

                dataSource.marketGetList().documents.forEach { document ->
                    document.toObject(MarketDTO::class.java)?.let { item ->
                        item.marketID = document.id
                        item.price = item.price?.let { Util.money(it) }
                        items.add(item)
                    }
                    /*
                    items.add(
                        MarketDTO(
                            marketID = it.id,
                            sellerUID = it.getString("sellerUID"),
                            buyerUID = it.getString("buyerUID"),
                            title = it.getString("title"),
                            content = it.getString("content"),
                            price = it.getString("price")?.let { price -> Util.money(price) },
                            sold = it.getBoolean("sold"),
                            reservation = it.getBoolean("reservation"),
                            negotiation = it.getBoolean("negotiation"),
                            timeStamp = it.getLong("timeStamp"),
                            lookUpCount = it.getLong("lookUpCount"),
                            photos = it.get("photos") as List<String>
                            //var category: ???
                        )
                    )
                    val temp = it.get("photos")
                    Log.d("TLQkf", "${it.get("photos") as List<String>?}")

                     */
                }
                callback(Result(data = items, result = MarketSellResult(isSuccess = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }
    }

    fun modify(_item: MarketDTO, callback: (Result<List<MarketDTO>>) -> Unit) {
        CoroutineScope(IO).launch {
            try {
                dataSource.marketModifyItem(_item)
                callback(Result(data = null, result = MarketSellResult(isSuccess = true)))
            } catch (e: Exception) {
                callback(Result(data = null, error = e))
            }
        }
    }

    fun getUID(): String {
        return dataSource.getUID()
    }
}