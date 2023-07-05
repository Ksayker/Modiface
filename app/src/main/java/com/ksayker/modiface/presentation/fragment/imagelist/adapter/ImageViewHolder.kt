package com.ksayker.modiface.presentation.fragment.imagelist.adapter

import android.net.Uri
import android.view.View.OnClickListener
import com.ksayker.modiface.core.utils.getBitmap
import com.ksayker.modiface.databinding.ItemImageBinding
import com.ksayker.modiface.presentation.base.adapter.BaseViewHolder


class ImageViewHolder(
    private val binding: ItemImageBinding,
    private val onImageClicked: (Int) -> Unit,
    private val onDeleteImageClicked: (Int) -> Unit
) : BaseViewHolder<ImageItem>(binding) {

    private val onImageClickListener = OnClickListener {
        ifHasPosition { position -> onImageClicked(position) }
    }
    private val onDeleteImageClickListener = OnClickListener {
        ifHasPosition { position -> onDeleteImageClicked(position) }
    }

    override fun bind(item: ImageItem) {
        val context = binding.root.context
        val uri = Uri.parse(item.image.uri)
        val bitmap = context.getBitmap(uri)

        binding.ivImage.setImageBitmap(bitmap)
        binding.ivDelete.setOnClickListener(onDeleteImageClickListener)
        binding.root.setOnClickListener(onImageClickListener)
    }
}