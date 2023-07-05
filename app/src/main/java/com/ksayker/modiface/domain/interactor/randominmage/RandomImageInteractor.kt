package com.ksayker.modiface.domain.interactor.randominmage

import com.ksayker.modiface.domain.entity.UnsplashImage

interface RandomImageInteractor {

    suspend fun saveUnsplashImage(image: UnsplashImage)

    suspend fun getRandomImage(): UnsplashImage
}