package com.example.afop.data.dataSource

import android.content.Context
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.model.UserDTO
import com.example.afop.data.response.ErrorCode
import com.example.afop.data.response.Response
import com.example.afop.data.response.Result
import com.example.afop.service.RetrofitService
import com.google.firebase.iid.FirebaseInstanceId

/**
 * 각종 데이터 입/출력을 책임지는 클래스
 */
class DataSource {
    companion object {
        private lateinit var fcm: FirebaseInstanceId
        private lateinit var user: UserDTO

        fun init(context: Context) {
            fcm = FirebaseInstanceId.getInstance()
            PreferenceManager.init(context)
        }
    }

    private val tag = this::class.java.simpleName
    private val service = RetrofitClient.instance.create(RetrofitService::class.java)

    /**
     *  인증 관련
     */

    suspend fun login(data: Map<String, String>): Result<*> {
        return try {
            service.login(data)
        } catch (e: Exception) {
            Result(data = null, error = ErrorCode.FAILED_CONNECT_SERVER)
        }
    }

    suspend fun autoLogin() {

    }

    fun logout() {

    }

    suspend fun refreshFCMToken() {

    }

    suspend fun verifyEmail(email: String): Result<*> {
        return try {
            service.verifyEmail(email)
        } catch (e: Exception) {
            Result(data = null, error = ErrorCode.FAILED_CONNECT_SERVER)
        }
    }

    suspend fun verifyNickname(nickname: String): Result<*> {
        return try {
            service.verifyNickname(nickname)
        } catch (e: Exception) {
            Result(data = null, error = ErrorCode.FAILED_CONNECT_SERVER)
        }
    }

    suspend fun register(data: Map<String, String>): Result<*> {
        return try {
            service.register(data)
        } catch (e: Exception) {
            Result(data = null, error = ErrorCode.FAILED_CONNECT_SERVER)
        }
    }

    /**
     * 마켓 관련
     */

    //글 한 개 쓰기
    suspend fun marketPutItem(item: MarketDTO) {

    }

    //글 한 개 읽기
    suspend fun marketGetItem(marketID: String) {

    }

    //글 한 개 수정
    suspend fun marketModifyItem(item: MarketDTO) {

    }

    suspend fun marketGetList(query: String) {

    }

    //채팅 관련

    //커뮤니티 관련

    //위치 관련

    /**
     * preference 관련
     */


}

/*
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date()); // Find todays date
 */