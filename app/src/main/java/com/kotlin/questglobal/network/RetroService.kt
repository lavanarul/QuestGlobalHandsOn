package com.kotlin.questglobal.network

import com.kotlin.questglobal.models.GetEventResponse
import com.kotlin.questglobal.models.Login
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RetroService {

    @POST("/api/authaccount/login")
    suspend fun loginApi(@Body login: Login): Response<GetEventResponse>

    companion object {
        const val BASE_URL="http://restapi.adequateshop.com"
        var retrofitService: RetroService? = null
        var interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        var okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()


        fun getInstance() : RetroService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                retrofitService = retrofit.create(RetroService::class.java)
            }
            return retrofitService!!
        }

    }
}