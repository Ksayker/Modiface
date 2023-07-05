package com.ksayker.modiface.data.source

import com.ksayker.modiface.data.database.dao.ImageDao
import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.domain.source.ImageSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageSourceImpl @Inject constructor(
    private val imageDao: ImageDao,
) : ImageSource {

    override suspend fun addNewImage(image: Image) {
        imageDao.addImage(image)
    }

    override suspend fun deleteImage(image: Image) {
        imageDao.deleteImage(image)
    }

    override fun getImages(): Flow<List<Image>> = imageDao.getImages()
}