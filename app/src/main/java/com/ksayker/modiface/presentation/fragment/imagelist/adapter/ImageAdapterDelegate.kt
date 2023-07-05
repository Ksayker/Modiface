package com.ksayker.modiface.presentation.fragment.imagelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ksayker.modiface.databinding.ItemImageBinding
import com.ksayker.modiface.presentation.base.adapter.BaseAdapterDelegate
import com.ksayker.modiface.presentation.base.adapter.BaseItem

class ImageAdapterDelegate(
    private val onImageClicked: (Int) -> Unit,
    private val onDeleteImageClicked: (Int) -> Unit
) : BaseAdapterDelegate<ImageItem, BaseItem<ImageItem>, ImageViewHolder>() {
    override fun isForViewType(
        item: BaseItem<ImageItem>,
        items: MutableList<BaseItem<ImageItem>>,
        position: Int
    ) = item is ImageItem

    override fun onCreateViewHolder(parent: ViewGroup): ImageViewHolder =
        ImageViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onImageClicked,
            onDeleteImageClicked
        )
}