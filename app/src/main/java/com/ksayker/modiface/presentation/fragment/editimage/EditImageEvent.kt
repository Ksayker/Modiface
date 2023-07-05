package com.ksayker.modiface.presentation.fragment.editimage

import com.ksayker.modiface.domain.entity.filter.Filter
import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.presentation.base.BaseEvent

sealed class EditImageEvent : BaseEvent {

    data class OnFilterClicked(
        val filter: Filter
    ) : EditImageEvent()

    data class OnSaveClicked(
        val filter: Filter,
        val image: Image
    ) : EditImageEvent()
}