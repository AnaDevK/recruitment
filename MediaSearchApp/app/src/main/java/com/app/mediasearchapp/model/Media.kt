package com.app.mediasearchapp.model


import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: List<Result>
)