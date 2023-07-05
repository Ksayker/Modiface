package com.ksayker.modiface.data.source

import android.content.Context
import android.graphics.Color
import com.ksayker.modiface.R
import com.ksayker.modiface.core.annotation.AppContext
import com.ksayker.modiface.domain.entity.filter.Filter
import com.ksayker.modiface.domain.entity.filter.InvertFilter
import com.ksayker.modiface.domain.entity.filter.NoiseFilter
import com.ksayker.modiface.domain.entity.filter.TintFilter
import com.ksayker.modiface.domain.source.FilterSource
import javax.inject.Inject

class FilterSourceImpl @Inject constructor(
    @AppContext context: Context
) : FilterSource {

    private val filters: List<Filter> = listOf(
        TintFilter(Color.RED, context.getString(R.string.filter_colorRed)),
        TintFilter(Color.GREEN, context.getString(R.string.filter_colorGreen)),
        TintFilter(Color.BLUE, context.getString(R.string.filter_colorBlue)),
        NoiseFilter(Color.WHITE, context.getString(R.string.filter_noise)),
        InvertFilter(Color.WHITE, context.getString(R.string.filter_invert))
    )

    override suspend fun getFilters() = filters
}