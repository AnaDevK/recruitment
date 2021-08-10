package com.app.mediasearchapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://itunes.apple.com/"

val retrofitBuilder = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ServiceMediaApi {
    val retrofitService: MediaApi by lazy {
        retrofitBuilder.create(
            MediaApi::class.java
        )
    }
}
