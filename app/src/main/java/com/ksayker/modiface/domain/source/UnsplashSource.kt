package com.ksayker.modiface.domain.source

import com.ksayker.modiface.domain.entity.UnsplashImage

interface UnsplashSource {

    suspend fun getRandomImage(): UnsplashImage
}