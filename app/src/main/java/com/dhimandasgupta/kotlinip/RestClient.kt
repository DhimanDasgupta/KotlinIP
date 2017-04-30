package com.dhimandasgupta.kotlinip

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by dhimandasgupta on 30/04/17.
 */
class RestClient {
    val BASE_URL = "http://httpbin.org"

    fun createApi() : ApiService {
        val retrofit = Retrofit.Builder().
                baseUrl(BASE_URL).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                build()

        return retrofit.create(ApiService::class.java)
    }
}