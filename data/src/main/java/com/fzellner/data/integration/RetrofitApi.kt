package com.fzellner.data.integration

import com.fzellner.data.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

object RetrofitApi {

    private const val API_KEY = "apikey"
    private const val TIME_STAMP = "ts"
    private const val HASH = "hash"

    fun createNetworkClient(baseUrl: String, debug: Boolean = true) =
        retrofitClient(
            baseUrl,
            httpClient(debug)
        )

    private fun httpClient(debug: Boolean): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        if (debug) {
            val httpLoggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        clientBuilder.addInterceptor {
            val request = it.request()

            val url =
                request.url().newBuilder().addQueryParameter(API_KEY, BuildConfig.API_PUBLIC_KEY)
                    .addQueryParameter(TIME_STAMP, getTimeStamp())
                    .addQueryParameter(HASH, generateMD5Hash())
                    .build()

            val builder = request.newBuilder()
                .url(url)
                .method(request.method(), request.body())

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


    private fun getTimeStamp(): String {
        val calendar = Calendar.getInstance()
        return SimpleDateFormat("yyyyMMdd").format(calendar.time)
    }

    private fun generateMD5Hash(): String {
        val input = getTimeStamp() + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16)
            .padStart(32, '0')
    }
}