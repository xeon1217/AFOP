package com.example.afop.service

import com.example.afop.data.model.MarketDTO
import com.example.afop.data.model.UserDTO
import com.example.afop.data.response.Result
import okhttp3.MultipartBody
import retrofit2.http.*

interface RetrofitService {
    /**
     *  login 관련
     */
    @POST("/auth/login")
    suspend fun login(@Body data: Map<String, String>?): Result<UserDTO>
    @POST("/auth/auto-login")
    suspend fun autoLogin(@Header("X-AUTH-TOKEN") token: String): Result<UserDTO>

    /**
     * register 관련
     */
    @POST("/auth/register")
    suspend fun register(@Body data: Map<String, String>): Result<*>
    @GET("/auth/register/verify")
    suspend fun verifyEmail(@Query("email") email: String): Result<*>
    @GET("/auth/register/verify")
    suspend fun verifyNickname(@Query("nickname") nickname: String): Result<*>

    /**
     * market 관련
     */
    @POST("/market/item")
    suspend fun marketPutItem(@Header("X-AUTH-TOKEN") token: String, @Body item: MarketDTO): Result<MarketDTO>
    @PUT("market/item")
    suspend fun marketModifyItem(@Header("X-AUTH-TOKEN") token: String, @Body item: MarketDTO): Result<MarketDTO>
    @GET("/market/item")
    suspend fun marketGetItem(@Query("market_id") marketID: Long): Result<MarketDTO>
    @GET("/market/items")
    suspend fun marketGetList(@Query("title") title: String?, @Query("last_id_cursor") last_id_cursor: Long?): Result<ArrayList<MarketDTO?>>

    /**
     * 파일 업로드/다운로드 관련
     */
    @Multipart
    @POST("/files/upload")
    suspend fun filePutItem(@Header("X-AUTH-TOKEN") token: String, @Part file: MultipartBody.Part): Result<*>
    @Multipart
    @POST("/files/uploads")
    suspend fun filePutList(@Header("X-AUTH-TOKEN") token: String, @Part files: ArrayList<MultipartBody.Part>): Result<*>
    @Multipart
    @POST("/files/download/{fileName}")
    suspend fun fileGetList()
}