package com.ksayker.modiface.presentation.fragment.imagelist.adapter

import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.presentation.base.adapter.BaseAdapter
import com.ksayker.modiface.presentation.base.adapter.BaseItem

class ImageAdapter(
    onImageClocked: (image: Image) -> Unit,
    onDeleteImageClocked: (image: Image) -> Unit,
) : BaseAdapter<ImageItem, BaseItem<ImageItem>, ImageViewHolder>(
        object : BaseItem<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem) = oldItem.image == newItem.image

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem) = oldItem == newItem
        }
    ) {

    init {
        addDelegate(
            ImageAdapterDelegate(
                onImageClicked = {
                    onImageClocked(items[it].image)
                },
                onDeleteImageClicked = {
                    onDeleteImageClocked(items[it].image)
                }
            )
        )
    }
}