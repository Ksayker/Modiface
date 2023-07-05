package com.ksayker.modiface.presentation.fragment.editphoto

import com.ksayker.modiface.domain.entity.filter.Filter
import com.ksayker.modiface.presentation.base.BaseEvent

sealed class EditPhotoEvent : BaseEvent {

    data class OnFilterClicked(
        val filter: Filter
    ) : EditPhotoEvent()
}