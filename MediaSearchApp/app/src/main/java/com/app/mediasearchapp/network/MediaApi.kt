package com.app.mediasearchapp.network

import com.app.mediasearchapp.model.Media
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaApi {
    @GET("search?")
    suspend fun getMediaData(
        @Query("term") term: String,
        @Query("media") media: String
    ): Response<Media>
}