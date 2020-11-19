package com.example.afop.data.repository

import android.util.Log
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.response.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * 마켓 관련 레포지토리
 */
class MarketRepository(private val dataSource: DataSource) {
    fun sell(item: MarketDTO, callback: (Result<*>) -> Unit) {
        CoroutineScope(IO).launch {
            var mItem: MarketDTO

            mItem = item.copy(
                sellerUID = DataSource.getUser().uid,
                timeStamp = Date().time
            )
            Log.e("aaa", "")
            Log.e("aaa", "${DataSource.getUser().uid}")
            Log.e("aaa", "${mItem.sellerUID}")

            if (item.images.size > 0) {
                val imageList: ArrayList<MultipartBody.Part> = ArrayList()
                val newFileNames: ArrayList<String> = ArrayList()

                for (i in 0 until item.images.size) {
                    val file = File(item.images[i])
                    val newFileName =
                        "${item.sellerUID}-${UUID.randomUUID()}.${item.images[i].split(".").last()}"
                    newFileNames.add(newFileName)

                    val newFile =
                        file.copyTo(target = File("${dataSource.getCacheDir()}/${newFileName}"))
                    val requestFile: RequestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), newFile)
                    val uploadFile =
                        MultipartBody.Part.createFormData("files", newFile.name, requestFile)
                    imageList.add(uploadFile)

                    Log.d("list", newFileName)
                    Log.d("cache", "${dataSource.getCacheDir()}/${newFileName}")
                }
                mItem = mItem.copy(images = newFileNames)
                dataSource.filePutList(imageList)
            }

            dataSource.marketPutItem(item = mItem).apply {
                response?.let { response ->
                    callback(Result(data = data, response = response))
                }
                error?.let { error ->
                    callback(Result(data = data, error = error))
                }
            }
        }
    }

    fun getList(
        title: String? = null,
        last_id_cursor: Long?,
        callback: (Result<ArrayList<MarketDTO?>>) -> Unit
    ) {
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

    fun modify(item: MarketDTO, callback: (Result<MarketDTO>) -> Unit) {
        CoroutineScope(IO).launch {
            dataSource.marketModifyItem(item = item).apply {
                response?.let { response ->
                    callback(Result(data = data, response = response))
                }
                error?.let { error ->
                    callback(Result(data = data, error = error))
                }
            }
        }
    }

    fun getUID(): String {
        return DataSource.getUser().uid
    }
}