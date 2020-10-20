package com.nanotechnology.covid_19statistic.api

import com.nanotechnology.covid_19statistic.db.Statistic
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Covid19Service {

    @GET("v2/all")
    suspend fun getGlobalStatistic(): Statistic
    companion object {
        private const val BASE_URL = "https://corona.lmao.ninja/"

        fun create(): Covid19Service {
            val logger = HttpLoggingInterceptor().apply { level =
                HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Covid19Service::class.java)
        }
    }
}


