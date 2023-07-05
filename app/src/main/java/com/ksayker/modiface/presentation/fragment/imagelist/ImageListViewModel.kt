package com.ksayker.modiface.presentation.fragment.imagelist

import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.domain.interactor.imagelist.ImageListInteractor
import com.ksayker.modiface.domain.provider.DispatcherProvider
import com.ksayker.modiface.presentation.base.BaseViewModel
import com.ksayker.modiface.presentation.fragment.imagelist.adapter.ImageItem
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.flowOn

class ImageListViewModel @AssistedInject constructor(
    initialState: ImageListState,
    private val interactor: ImageListInteractor,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<ImageListState, ImageListEvent, ImageListLabel>(initialState) {

    init {
        observeImages()
    }

    override fun onEvent(event: ImageListEvent) {
        when (event) {
            is ImageListEvent.AddNewImage -> addNewImage(event.uri)
            is ImageListEvent.DeleteImage -> deleteImage(event.image)
        }
    }

    private fun deleteImage(image: Image) {
        launch {
            interactor.deleteImage(image)
        }
    }

    private fun addNewImage(uri: String) {
        launch {
            interactor.addNewImage(Image(uri = uri))
        }
    }

    private fun observeImages() {
        launch {
            interactor.getImages()
                .flowOn(dispatcherProvider.defaultDispatcher)
                .collect { images ->
                    updateState(
                        currentState.copy(
                            items = images.mapIndexed { index, image -> ImageItem(index, image) }
                        )
                    )
                }
        }
    }
}