package com.ksayker.modiface.presentation.fragment.imagelist

import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.presentation.base.BaseEvent

sealed class ImageListEvent : BaseEvent {

    data class AddNewImage(val uri: String) : ImageListEvent()

    data class DeleteImage(val image: Image) : ImageListEvent()
}