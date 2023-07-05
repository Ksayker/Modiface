package com.ksayker.modiface.presentation.fragment.imagelist.adapter

import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.presentation.base.adapter.BaseItem

data class ImageItem(
    val id: Int,
    val image: Image
) : BaseItem<ImageItem>()