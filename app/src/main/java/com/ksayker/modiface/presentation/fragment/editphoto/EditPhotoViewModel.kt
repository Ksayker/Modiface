package com.ksayker.modiface.presentation.fragment.editphoto

import com.ksayker.modiface.domain.entity.filter.Filter
import com.ksayker.modiface.domain.interactor.editimage.EditImageInteractor
import com.ksayker.modiface.presentation.adapter.filter.FilterItem
import com.ksayker.modiface.presentation.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class EditPhotoViewModel @AssistedInject constructor(
    @Assisted initialSate: EditPhotoState,
    private val editImageInteractor: EditImageInteractor
) : BaseViewModel<EditPhotoState, EditPhotoEvent, EditPhotoLabel>(initialSate) {

    init {
        getFilters()
    }

    override fun onEvent(event: EditPhotoEvent) {
        when (event) {
            is EditPhotoEvent.OnFilterClicked -> selectFilter(event.filter)
        }
    }

    private fun selectFilter(filter: Filter) {
        val selectedFilter = currentState.filterItems.find { it.isSelected }?.filter
        updateState(
            currentState.copy(
                filterItems = currentState.filterItems.map {
                    FilterItem(
                        id = it.id,
                        filter = it.filter,
                        isSelected = it.filter == filter && filter != selectedFilter
                    )
                }
            )
        )
    }

    private fun getFilters() {
        launch {
            val filters = editImageInteractor.getFilters()
            updateState(
                currentState.copy(
                    filterItems = filters.mapIndexed { index, filter ->
                        FilterItem(index, filter, false)
                    })
            )
        }
    }
}