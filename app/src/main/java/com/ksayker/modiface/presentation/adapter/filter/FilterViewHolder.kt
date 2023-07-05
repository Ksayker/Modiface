package com.ksayker.modiface.presentation.adapter.filter

import android.view.View.OnClickListener
import com.ksayker.modiface.databinding.ItemFilterBinding
import com.ksayker.modiface.presentation.base.adapter.BaseViewHolder

class FilterViewHolder(
    private val binding: ItemFilterBinding,
    private val onItemClicked: (Int) -> Unit
) : BaseViewHolder<FilterItem>(binding) {

    private val onClickListener = OnClickListener {
        ifHasPosition { position -> onItemClicked(position) }
    }

    override fun bind(item: FilterItem) {
        with(binding) {
            root.setOnClickListener(onClickListener)

            tvDescription.text = item.filter.getRepresentationDescription()
            vBackground.setBackgroundColor(item.filter.getRepresentationColor())
            cbSelected.isChecked = item.isSelected
        }
    }
}