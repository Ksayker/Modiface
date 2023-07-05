package com.ksayker.modiface.presentation.fragment.editphoto

import android.os.Parcelable
import com.ksayker.modiface.presentation.base.BaseState
import com.ksayker.modiface.presentation.adapter.filter.FilterItem
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditPhotoState(
    @IgnoredOnParcel
    val filterItems: List<FilterItem> = emptyList(),
) : BaseState(), Parcelable