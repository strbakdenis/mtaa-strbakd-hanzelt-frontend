package com.example.mtaa_strbakd_hanzelt

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @POST("/users/add")
    suspend fun register(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("/users/login")
    suspend fun login(@Body requestBody: RequestBody): Response<ResponseBody>

    @PUT("/users/update/")
    suspend fun update(@Query("token") token: String, @Body requestBody: RequestBody): Response<ResponseBody>

    @DELETE("/users/delete/")
    suspend fun delete(@Query("token") token: String): Response<ResponseBody>

    @POST("/activities/add/")
    suspend fun add(@Query("token") token: String, @Body requestBody: RequestBody): Response<ResponseBody>

}