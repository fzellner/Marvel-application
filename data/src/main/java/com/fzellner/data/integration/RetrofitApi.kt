package com.fzellner.data.integration

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitApi {

    private const val AUTHORIZATION = "Authorization"

    fun createNetworkClient(baseUrl: String, debug: Boolean = true) =
        retrofitClient(
            baseUrl,
            httpClient(debug)
        )

    private fun httpClient( debug: Boolean): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        if (debug) {
            val httpLoggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        clientBuilder.addInterceptor {
            val request = it.request()
            val builder = request.newBuilder().method(request.method(), request.body())
            return@addInterceptor it.proceed(builder.build())
        }

        return clientBuilder
            .build()
    }

    private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
}