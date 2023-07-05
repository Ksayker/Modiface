package com.ksayker.modiface.presentation.fragment.imagelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ksayker.modiface.domain.interactor.imagelist.ImageListInteractor
import com.ksayker.modiface.domain.provider.DispatcherProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ImageListViewModelFactory @AssistedInject constructor(
    @Assisted private val initialState: ImageListState,
    private val interactor: ImageListInteractor,
    private val dispatcherProvider: DispatcherProvider
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == ImageListViewModel::class.java)

        return ImageListViewModel(initialState, interactor, dispatcherProvider) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted initialState: ImageListState): ImageListViewModelFactory
    }
}