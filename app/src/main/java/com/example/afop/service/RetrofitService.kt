package com.example.afop.service

import com.example.afop.data.response.Response
import com.example.afop.data.response.Result
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    /**
     *  login 관련
     */
    @POST("/auth/login")
    suspend fun login(@Body data: Map<String, String>): Result<*>

    /**
     * register 관련
     */
    @POST("/auth/register")
    suspend fun register(@Body data: Map<String, String>): Result<*>
    @GET("/auth/register/verify-email/{email}")
    suspend fun verifyEmail(@Path("email") email: String): Result<*>
    @GET("/auth/register/verify-nickname/{nickname}")
    suspend fun verifyNickname(@Path("nickname") email: String): Result<*>
}