package com.ksayker.modiface.data.remote

import com.ksayker.modiface.domain.entity.UnsplashImage
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

const val BASE_URL = "https://api.unsplash.com/"
const val BASE_IMAGE_URL = "https://images.unsplash.com/"
const val PATH_RANDOM_PHOTO_WITH_CLIENT_ID = "photos/random?client_id=i9UO-_Yb_9IF8j1ME2AyOX-BxhR0bIKrN0kBwmcJG30"

interface UnsplashApiService {

    @GET(PATH_RANDOM_PHOTO_WITH_CLIENT_ID)
    suspend fun getRandomPhoto(): UnsplashImage

    @GET
    suspend fun downloadImage(@Url url: String): ResponseBody
}