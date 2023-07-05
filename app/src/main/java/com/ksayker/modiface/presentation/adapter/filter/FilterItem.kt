package com.ksayker.modiface.presentation.adapter.filter

import com.ksayker.modiface.domain.entity.filter.Filter
import com.ksayker.modiface.presentation.base.adapter.BaseItem

data class FilterItem(
    val id: Int,
    val filter: Filter,
    val isSelected: Boolean
) : BaseItem<FilterItem>()