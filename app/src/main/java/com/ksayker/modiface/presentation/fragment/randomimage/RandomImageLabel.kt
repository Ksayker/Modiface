package com.ksayker.modiface.presentation.fragment.randomimage

import com.ksayker.modiface.presentation.base.BaseLabel

sealed class RandomImageLabel : BaseLabel {

    object MessageImageSaved : RandomImageLabel()

    object MessageImageSaveError : RandomImageLabel()

    object MessageImageLoadingError : RandomImageLabel()

    object Back : RandomImageLabel()
}