package com.ksayker.modiface.presentation.fragment.imagelist

import android.os.Parcelable
import com.ksayker.modiface.presentation.base.BaseState
import com.ksayker.modiface.presentation.fragment.imagelist.adapter.ImageItem
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageListState(
    @IgnoredOnParcel
    val items: List<ImageItem> = emptyList()
) : BaseState(), Parcelable