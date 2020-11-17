package com.example.afop.data.dataSource

import android.content.Context
import android.util.Log
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.model.UserDTO
import com.example.afop.data.response.ErrorCode
import com.example.afop.data.response.Response
import com.example.afop.data.response.Result
import com.example.afop.service.RetrofitService
import com.google.firebase.iid.FirebaseInstanceId
import retrofit2.http.*
import java.net.SocketTimeoutException

/**
 * 각종 데이터 입/출력을 책임지는 클래스
 */
class DataSource {
    companion object {
        private lateinit var fcm: FirebaseInstanceId
        private var user: UserDTO? = null

        fun init(context: Context) {
            fcm = FirebaseInstanceId.getInstance()
            PreferenceManager.init(context)
        }

        fun exit() {
            if(isLogin() && !getAutoLogin()) {
                logout()
            }
        }

        fun logout() {
            if (isLogin()) {
                user = null
                setAutoLogin(false)
                removeToken()
            }
        }

        fun isLogin(): Boolean {
            return user != null
        }

        fun setAutoLogin(value: Boolean) {
            PreferenceManager.setBoolean("auto-login", value)
        }

        fun getAutoLogin(): Boolean {
            return PreferenceManager.getBoolean("auto-login")
        }

        fun getUser(): UserDTO {
            user?.let { user ->
                return user
            }
            return UserDTO("", "", "",  "", "", "")
        }

        private fun removeToken() {
            PreferenceManager.removeKey("token")
        }
    }

    private val tag = this::class.java.simpleName
    private val service = RetrofitClient.instance.create(RetrofitService::class.java)

    /**
     *  인증 관련
     */

    private fun putToken(value: String) {
        PreferenceManager.setString("token", value)
    }

    private fun getToken(): String {
        return PreferenceManager.getString("token")
    }

    suspend fun login(loginData: Map<String, String>): Result<UserDTO> {
        return try {
            service.login(loginData).apply {
                user = data
                if (getAutoLogin()) {
                    putToken(getUser().token)
                }
            }
        } catch (e: Exception) {
            exceptionHandler(e) as Result<UserDTO>
        }
    }

    suspend fun autoLogin(): Result<UserDTO> {
        return try {
            service.autoLogin(getToken()).apply {
                user = data
                if (getAutoLogin()) {
                    putToken(getUser().token)
                }
            }
        } catch (e: Exception) {
            exceptionHandler(e) as Result<UserDTO>
        }
    }

    suspend fun refreshFCMToken() {

    }

    //이메일 중복 확인
    suspend fun verifyEmail(email: String): Result<*> {
        return try {
            service.verifyEmail(email)
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }

    //닉네임 중복 확인
    suspend fun verifyNickname(nickname: String): Result<*> {
        return try {
            service.verifyNickname(nickname)
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }

    //회원가입
    suspend fun register(data: Map<String, String>): Result<*> {
        return try {
            service.register(data)
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }

    /**
     * 마켓 관련
     */

    //글 한 개 쓰기
    suspend fun marketPutItem(item: MarketDTO): Result<*> {
        return try {
            service.marketPutItem(token = getUser().token, item)
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }

    //글 한 개 수정
    suspend fun marketModifyItem(item: MarketDTO, marketID: String): Result<*> {
        return try {
            service.marketModifyItem(token = getUser().token, item = item, marketID = marketID)
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }

    //글 한 개 읽기
    suspend fun marketGetItem(marketID: Long): Result<MarketDTO> {
        return try {
            service.marketGetItem(marketID)
        } catch (e: Exception) {
            exceptionHandler(e) as Result<MarketDTO>
        }
    }

    suspend fun marketGetList(title: String?, last_id_cursor: Long?): Result<ArrayList<MarketDTO?>> {
        return try {
            service.marketGetList(title = title, last_id_cursor = last_id_cursor)
        } catch (e: Exception) {
            exceptionHandler(e) as Result<ArrayList<MarketDTO?>>
        }
    }

    //채팅 관련

    //커뮤니티 관련

    //위치 관련

    /**
     * preference 관련
     */

    /**
     * 예외처리 핸들러
     */

    //시스템 상 발생한 예외에 대해 처리하는 핸들러
    private fun exceptionHandler(exception: Exception): Result<*> {
        return when (exception) {
            is SocketTimeoutException -> {
                Result(data = null, error = ErrorCode.FAILED_CONNECT_SERVER)
            }
            else -> {
                Log.d("Exception Handler", "$exception")
                Result(data = null, error = ErrorCode.NOT_DEFINE_ERROR)
            }
        }
    }
}

/*
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date()); // Find todays date
 */