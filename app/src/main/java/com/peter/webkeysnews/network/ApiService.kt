package com.peter.webkeysnews.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.peter.webkeysnews.pojo.JsonNestedResponse
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @Headers("Cache-Control: max-age=640000")
    @GET("v2/everything")
    fun getResponse(@Query("q") type: String, @Query("apiKey") apikey: String):
            Deferred<JsonNestedResponse>
    object NewsApi {
        val retrofitService: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}