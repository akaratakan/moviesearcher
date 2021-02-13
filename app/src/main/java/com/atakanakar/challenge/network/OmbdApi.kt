package com.atakanakar.challenge.network

import com.atakanakar.challenge.BuildConfig
import com.atakanakar.challenge.network.model.id.DetailResponseObject
import com.atakanakar.challenge.network.model.search.SearchResponse
import retrofit2.http.*

interface OmbdApi {

    @GET(".")
    suspend fun searchMovie(
        @Query("s") keyword: String,
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
    ): SearchResponse

    @GET(".")
    suspend fun getMovieById(
        @Query("i") imdbId: String?,
        @Query("plot") plot: String?="full",
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
    ): DetailResponseObject
}