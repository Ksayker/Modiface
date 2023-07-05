package com.ksayker.modiface.domain.source

import android.graphics.Bitmap
import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.domain.entity.UnsplashImage
import com.ksayker.modiface.domain.entity.filter.Filter

interface FileSource {

    suspend fun saveImage(filter: Filter, image: Image): String

    suspend fun saveBitmap(bitmap: Bitmap): String

    suspend fun saveUnsplashImage(image: UnsplashImage): String
}