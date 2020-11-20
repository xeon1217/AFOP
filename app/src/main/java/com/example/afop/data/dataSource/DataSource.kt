package com.example.afop.data.dataSource

import android.content.Context
import android.util.Log
import com.example.afop.data.model.MarketDTO
import com.example.afop.data.model.UserDTO
import com.example.afop.data.response.ErrorCode
import com.example.afop.data.response.Result
import com.example.afop.service.RetrofitService
import com.google.firebase.iid.FirebaseInstanceId
import okhttp3.*
import retrofit2.http.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.SocketTimeoutException

/**
 * 각종 데이터 입/출력을 책임지는 클래스
 */
class DataSource {
    companion object {
        private lateinit var cache: File
        private lateinit var fcm: FirebaseInstanceId
        private var user: UserDTO? = null

        fun init(context: Context) {
            cache = context.cacheDir
            fcm = FirebaseInstanceId.getInstance()
            PreferenceManager.init(context)
        }

        fun exit() {
            if (isLogin() && !getAutoLogin()) {
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
            return UserDTO(uid = "", token = "")
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

    suspend fun login(loginData: Map<String, String>?): Result<UserDTO> {
        return try {
            if (getAutoLogin() && loginData.isNullOrEmpty()) {
                Log.e("tt", "!${loginData}")
                service.autoLogin(token = getToken())
            } else {
                Log.e("tt", "?${loginData}")
                service.login(data = loginData)
            }.apply {
                user = data
                putToken(getUser().token)
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
            service.verifyEmail(email = email)
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }

    //닉네임 중복 확인
    suspend fun verifyNickname(nickname: String): Result<*> {
        return try {
            service.verifyNickname(nickname = nickname)
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }

    //회원가입
    suspend fun register(data: Map<String, String>): Result<*> {
        return try {
            service.register(data = data)
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }

    /**
     * 마켓 관련
     */

    //글 한 개 쓰기
    suspend fun marketPutItem(item: MarketDTO): Result<MarketDTO> {
        return try {
            service.marketPutItem(token = getUser().token, item = item)
        } catch (e: Exception) {
            exceptionHandler(e) as Result<MarketDTO>
        }
    }

    //글 한 개 수정
    suspend fun marketModifyItem(item: MarketDTO): Result<MarketDTO> {
        return try {
            service.marketModifyItem(token = getUser().token, item = item)
        } catch (e: Exception) {
            exceptionHandler(e) as Result<MarketDTO>
        }
    }

    //글 한 개 읽기
    suspend fun marketGetItem(marketID: Long): Result<MarketDTO> {
        return try {
            service.marketGetItem(marketID = marketID)
        } catch (e: Exception) {
            exceptionHandler(e) as Result<MarketDTO>
        }
    }

    suspend fun marketGetList(
        title: String?,
        last_id_cursor: Long?
    ): Result<ArrayList<MarketDTO?>> {
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
     * 파일 관련
     */

    @Multipart
    suspend fun filePutItem(
        map: HashMap<String, RequestBody>,
        file: MultipartBody.Part
    ): Result<*> {
        return try {
            service.filePutItem(token = getToken(), file = file)
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }

    @Multipart
    suspend fun filePutList(files: ArrayList<MultipartBody.Part>): Result<*> {
        return try {
            service.filePutList(token = getToken(), files = files)
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }

    suspend fun fileGetList() {

    }

    suspend fun fileGetItem(file: String) {
        try {
            service.fileGetItem(file = file).apply {
                body()?.apply {
                    val newFile = File("${getCacheDir()}", file)
                    if (!newFile.exists()) {
                        var iS: InputStream? = null
                        var oS: OutputStream? = null
                        try {
                            iS = byteStream()
                            oS = FileOutputStream(newFile)
                            val data = ByteArray(4096)
                            var count = 0
                            while (count != -1) {
                                count = iS?.read(data)!!
                                oS.write(data, 0, count)
                            }
                            oS.flush()

                        } catch (e: Exception) {

                        } finally {
                            iS?.close()
                            oS?.close()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("get", "error")
        }
    }

    fun getCacheDir(): File {
        return cache
    }

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
                Log.d("Exception!", "$exception")
                Result(data = null, error = ErrorCode.NOT_DEFINE_ERROR)
            }
        }
    }
}

/*
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date()); // Find todays date
 */
