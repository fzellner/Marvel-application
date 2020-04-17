package com.fzellner.data.api

import com.fzellner.data.domain.model.HeroListResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {



    @GET("v1/public/characters")
    fun getCharacterList(
        @Query("ts") timeStamp:String,
        @Query("apikey") apiKey:String,
        @Query("hash") hash:String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Deferred<Response<HeroListResponse>>


}