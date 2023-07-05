package com.ksayker.modiface.domain.interactor.editimage

import com.ksayker.modiface.domain.entity.filter.Filter
import com.ksayker.modiface.domain.entity.Image

interface EditImageInteractor {

    suspend fun getFilters(): List<Filter>

    suspend fun saveImage(filter: Filter, image: Image)
}