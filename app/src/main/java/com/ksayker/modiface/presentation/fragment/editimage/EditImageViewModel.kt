package com.ksayker.modiface.presentation.fragment.editimage

import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.domain.entity.filter.Filter
import com.ksayker.modiface.domain.interactor.editimage.EditImageInteractor
import com.ksayker.modiface.presentation.adapter.filter.FilterItem
import com.ksayker.modiface.presentation.base.BaseViewModel

class EditImageViewModel(
    initialState: EditImageState,
    private val interactor: EditImageInteractor
) : BaseViewModel<EditImageState, EditImageEvent, EditImageLabel>(initialState) {

    init {
        getFilters()
    }

    override fun onEvent(event: EditImageEvent) {
        when (event) {
            is EditImageEvent.OnFilterClicked -> selectFilter(event.filter)
            is EditImageEvent.OnSaveClicked -> saveImage(event.filter, event.image)
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

    private fun saveImage(filter: Filter, image: Image) {
        launch {
            try {
                interactor.saveImage(filter, image)
                publishLabel(EditImageLabel.MessageImageSaved)
                publishLabel(EditImageLabel.Back)
            } catch (e: Exception) {
                publishLabel(EditImageLabel.MessageImageSaveError)
            }
        }
    }

    private fun getFilters() {
        launch {
            val filters = interactor.getFilters()
            updateState(
                currentState.copy(
                    filterItems = filters.mapIndexed { index, filter ->
                        FilterItem(index, filter, false)
                    })
            )
        }
    }
}
