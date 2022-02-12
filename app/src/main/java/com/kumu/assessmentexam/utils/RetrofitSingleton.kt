package com.kumu.assessmentexam.utils

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitSingleton {

    val gson = GsonBuilder()
        .create()

    private val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(10, TimeUnit.MINUTES)
        .retryOnConnectionFailure(true)
        .readTimeout(10, TimeUnit.MINUTES)
        .writeTimeout(10, TimeUnit.MINUTES)
        .build()


    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    inline fun <reified T> get(): T =
        retrofit.create(T::class.java)
}

fun Any?.toJson() =
    RetrofitSingleton.gson.toJson(this)

inline fun <reified T> String.fromJson() =
    RetrofitSingleton.gson.fromJson(this, T::class.java)