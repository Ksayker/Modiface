package com.ksayker.modiface.domain.interactor.imagelist

import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.domain.provider.DispatcherProvider
import com.ksayker.modiface.domain.source.ImageSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageListInteractorImpl @Inject constructor(
    private val imageSource: ImageSource,
    private val dispatcherProvider: DispatcherProvider
) : ImageListInteractor {

    override suspend fun addNewImage(image: Image) {
        withContext(dispatcherProvider.defaultDispatcher) {
            imageSource.addNewImage(image)
        }
    }

    override suspend fun deleteImage(image: Image) {
        withContext(dispatcherProvider.defaultDispatcher) {
            imageSource.deleteImage(image)
        }
    }

    override fun getImages(): Flow<List<Image>> = imageSource.getImages()
}