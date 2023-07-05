package com.ksayker.modiface.domain.source

import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.domain.entity.UnsplashImage
import kotlinx.coroutines.flow.Flow

interface ImageSource {

    suspend fun addNewImage(image: Image)

    suspend fun deleteImage(image: Image)

    fun getImages(): Flow<List<Image>>
}