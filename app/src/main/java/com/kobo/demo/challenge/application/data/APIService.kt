package com.kobo.demo.challenge.application.data

import com.kobo.demo.challenge.application.model.User
import com.kobo.demo.challenge.application.model.UserPage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {

    @GET("/dsandin/c8ed6c5a9486f311f4725f221b912220/raw/8c6d2d8e1f339d02e0ff3d2990560a4862c4beae/users_page_list")
    fun getUserPages(): Call<UserPage>


    @GET
    fun getUsers(@Url url: String): Call<List<User>>

    companion object {
        private const val BASE_URL = "https://gist.githubusercontent.com"

        fun create(): APIService {
          //  val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
  //              .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }
    }

}