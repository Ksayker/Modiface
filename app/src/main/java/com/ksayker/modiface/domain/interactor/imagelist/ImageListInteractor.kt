package com.ksayker.modiface.domain.interactor.imagelist

import com.ksayker.modiface.domain.entity.Image
import kotlinx.coroutines.flow.Flow

interface ImageListInteractor {

    suspend fun addNewImage(image: Image)

    suspend fun deleteImage(image: Image)

    fun getImages(): Flow<List<Image>>
}