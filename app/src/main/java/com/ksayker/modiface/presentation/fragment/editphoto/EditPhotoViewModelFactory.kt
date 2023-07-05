package com.ksayker.modiface.presentation.fragment.editphoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ksayker.modiface.domain.interactor.editimage.EditImageInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class EditPhotoViewModelFactory @AssistedInject constructor(
    @Assisted private val initialState: EditPhotoState,
    private val interactor: EditImageInteractor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == EditPhotoViewModel::class.java)

        return EditPhotoViewModel(initialState, interactor) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted initialState: EditPhotoState): EditPhotoViewModelFactory
    }
}