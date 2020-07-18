package com.anvios.android.web.anvios.main

import com.anvios.android.web.anvios.data.request.CreatePostRequest
import com.anvios.android.web.anvios.data.request.LoginRequest
import com.anvios.android.web.anvios.data.request.RegisterRequest
import com.anvios.android.web.anvios.data.response.*
import io.reactivex.Single
import retrofit2.http.*

interface AnviosApi {
    @POST("/validation/register")
    fun register(@Body requestBody: RegisterRequest): Single<RegisterResponse>

    @POST("/validation/login")
    fun login(@Body requestBody: LoginRequest): Single<LoginResponse>

    @POST("/content/post/create")
    fun createPost(
        @Header("device_token") token: String,
        @Body requestBody: CreatePostRequest
    ): Single<CreatePostResponse>

    @GET("/content/post")
    fun getPostsPage(@Query("page") page: Int): Single<List<PostResponse>>
}