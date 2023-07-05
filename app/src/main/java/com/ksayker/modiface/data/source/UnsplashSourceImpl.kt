package com.ksayker.modiface.data.source

import com.ksayker.modiface.data.remote.UnsplashApiService
import com.ksayker.modiface.domain.source.UnsplashSource
import javax.inject.Inject

class UnsplashSourceImpl @Inject constructor(
    private val unsplashApiService: UnsplashApiService
) : UnsplashSource {

    override suspend fun getRandomImage() = unsplashApiService.getRandomPhoto()
}