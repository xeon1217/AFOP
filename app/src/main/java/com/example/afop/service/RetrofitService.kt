package com.example.afop.service

import com.example.afop.data.model.MarketDTO
import com.example.afop.data.model.UserDTO
import com.example.afop.data.response.Result
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    /**
     *  auth 관련
     */

    @POST("/auth/login")
    suspend fun login(@Body data: Map<String, String>?): Result<UserDTO>
    @POST("/auth/auto-login")
    suspend fun autoLogin(@Header("X-AUTH-TOKEN") token: String): Result<UserDTO>
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
    suspend fun marketCreate(@Header("X-AUTH-TOKEN") token: String, @Body item: MarketDTO): Result<MarketDTO>
    @GET("/market/item")
    suspend fun marketRead(@Query("market_id") marketID: String): Result<MarketDTO>
    @GET("/market/items")
    suspend fun marketReads(@Query("title") title: String?, @Query("last_id_cursor") last_id_cursor: Long?): Result<ArrayList<MarketDTO?>>
    @PUT("/market/item")
    suspend fun marketUpdate(@Header("X-AUTH-TOKEN") token: String, @Body item: MarketDTO): Result<MarketDTO>
    suspend fun marketDelete()

    /**
     * meeting 관련
     */

    @POST
    suspend fun meetingCreate()
    @GET
    suspend fun meetingRead()
    @GET
    suspend fun meetingReads()
    @PUT
    suspend fun meetingUpdate()
    suspend fun meetingDelete()

    /**
     * community 관련
     */

    @POST
    suspend fun communityCreate()
    @GET
    suspend fun communityRead()
    @GET
    suspend fun communityReads()
    @PUT
    suspend fun communityUpdate()
    suspend fun communityDelete()

    /**
     * chatting 관련
     */

    @POST
    suspend fun chattingCreate()
    @GET
    suspend fun chattingRead()
    @GET
    suspend fun chattingReads()

    /**
     * 파일 업로드/다운로드 관련
     */

    @Multipart
    @POST("/files/upload")
    suspend fun fileCreate(@Header("X-AUTH-TOKEN") token: String, @Part file: MultipartBody.Part): Result<*>
    @Multipart
    @POST("/files/uploads")
    suspend fun fileCreates(@Header("X-AUTH-TOKEN") token: String, @Part files: ArrayList<MultipartBody.Part>): Result<*>
    @Streaming
    @GET("/files/download")
    suspend fun fileRead(@Query("file") file: String): Response<ResponseBody>

}