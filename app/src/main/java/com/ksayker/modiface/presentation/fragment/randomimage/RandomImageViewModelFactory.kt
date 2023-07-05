package com.ksayker.modiface.presentation.fragment.randomimage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ksayker.modiface.domain.interactor.editimage.EditImageInteractor
import com.ksayker.modiface.domain.interactor.randominmage.RandomImageInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class RandomImageViewModelFactory @AssistedInject constructor(
    @Assisted private val initialState: RandomImageState,
    private val interactor: RandomImageInteractor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == RandomImageViewModel::class.java)

        return RandomImageViewModel(initialState, interactor) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted initialState: RandomImageState): RandomImageViewModelFactory
    }
}