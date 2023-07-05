package com.ksayker.modiface.domain.source

import com.ksayker.modiface.domain.entity.filter.Filter

interface FilterSource {

    suspend fun getFilters(): List<Filter>
}