package com.example.afop.data.dataSource

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    companion object {
        private const val PROTOCOL_HTTP = "http://"
        private const val PROTOCOL_HTTPS = "https://"
        private const val PORT = "8081" // 포트
        private const val ADDRESS = "jungyoon.dynu.net" // 서버 주소
        private const val ADDRESS_LOCAL = "192.168.1.230" // 서버 주소
        private const val ADDRESS_PC = "192.168.1.10" // 서버 주소
        private const val ADDRESS_AVD = "10.0.2.2" // 서버 주소
        private const val ADDRESS_LOCALHOST = "localhost" // 서버 주소
        private const val BASE_URL = "${PROTOCOL_HTTP}${ADDRESS}:${PORT}"
        const val IMAGE_URL = "${BASE_URL}/files/"
        val gson = GsonBuilder().setLenient().create()
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS) // 연결에 성공하는데 기다리는 시간
            .readTimeout(30, TimeUnit.SECONDS) // 읽기에 성공하는데 기다리는 시간
            .writeTimeout(30, TimeUnit.SECONDS) // 쓰기에 성공하는데 기다리는 시간
            .build()
        val instance: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}