package com.ksayker.modiface.domain.entity

import com.google.gson.annotations.SerializedName

data class UnsplashImage(
    @SerializedName("id")
    val id: String?,
    @SerializedName("urls")
    val urls: UnsplashUrls?
)

data class UnsplashUrls(
    @SerializedName("thumb")
    val thumbnail: String?,
    @SerializedName("regular")
    val regular: String?
)