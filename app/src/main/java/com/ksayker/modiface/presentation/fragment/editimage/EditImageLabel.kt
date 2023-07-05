package com.ksayker.modiface.presentation.fragment.editimage

import com.ksayker.modiface.presentation.base.BaseLabel

sealed class EditImageLabel : BaseLabel {

    object MessageImageSaved : EditImageLabel()

    object MessageImageSaveError : EditImageLabel()

    object Back: EditImageLabel()
}