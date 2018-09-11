package com.andyyang.ipfs.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *Created by liuyang on 2018/9/3.
 */
object Network {

    private const val READ_TIMEOUT: Long = 60
    private const val CONNECT_TIMEOUT: Long = 5
    private const val BASE_URL = "https://www.baidu.com"

    val okHttpClient: OkHttpClient by lazy {
        createOkHttp()
    }
    private val retrofit: Retrofit by lazy {
        createRetrofit(okHttpClient, Gson, BASE_URL)
    }
    val Gson: Gson by lazy {
        GsonBuilder().create()
    }

    fun <T> createApi(api: Class<T>, baseUrl: String = BASE_URL): T {
        return retrofit.newBuilder().baseUrl(baseUrl).build().create(api)
    }

    private fun createOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build()
    }

    private fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson, baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(baseUrl)
                .build()
    }
}
