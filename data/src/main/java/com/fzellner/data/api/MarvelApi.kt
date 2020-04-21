package com.fzellner.data.api

import com.fzellner.data.domain.model.ComicResponse
import com.fzellner.data.domain.model.ComicResult
import com.fzellner.data.domain.model.HeroResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MarvelApi {



    @GET("v1/public/characters")
    fun getCharacterList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Deferred<Response<HeroResponse>>

    @GET("/v1/public/characters/{heroId}/comics")
    fun getComicsByHero(@Path("heroId") heroId:String): Deferred<Response<ComicResponse>>

}