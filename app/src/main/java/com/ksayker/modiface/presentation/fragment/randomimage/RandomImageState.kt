package com.ksayker.modiface.presentation.fragment.randomimage

import android.os.Parcelable
import com.ksayker.modiface.domain.entity.UnsplashImage
import com.ksayker.modiface.presentation.base.BaseState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomImageState(
    val shouldLoadNewImage: Boolean = false,
    @IgnoredOnParcel
    // TODO: refactor nullable
    val image: UnsplashImage? = null
) : BaseState(), Parcelable