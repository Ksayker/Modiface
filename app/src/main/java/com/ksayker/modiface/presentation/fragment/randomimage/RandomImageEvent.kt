package com.ksayker.modiface.presentation.fragment.randomimage

import com.ksayker.modiface.domain.entity.UnsplashImage
import com.ksayker.modiface.presentation.base.BaseEvent

sealed class RandomImageEvent : BaseEvent {

    data class SaveUnsplashImage(
        val image: UnsplashImage
    ) : RandomImageEvent()
}