package com.ksayker.modiface.presentation.fragment.editimage

import android.os.Parcelable
import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.presentation.adapter.filter.FilterItem
import com.ksayker.modiface.presentation.base.BaseState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditImageState(
    val image: Image,
    @IgnoredOnParcel
    val filterItems: List<FilterItem> = emptyList(),
) : BaseState(), Parcelable