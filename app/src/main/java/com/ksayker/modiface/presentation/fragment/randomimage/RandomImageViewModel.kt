package com.ksayker.modiface.presentation.fragment.randomimage

import androidx.lifecycle.LifecycleOwner
import com.ksayker.modiface.domain.entity.UnsplashImage
import com.ksayker.modiface.domain.interactor.randominmage.RandomImageInteractor
import com.ksayker.modiface.presentation.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class RandomImageViewModel @AssistedInject constructor(
    @Assisted initialSate: RandomImageState,
    private val interactor: RandomImageInteractor,
) : BaseViewModel<RandomImageState, RandomImageEvent, RandomImageLabel>(initialSate) {

    override fun onCreate(owner: LifecycleOwner) {
        launch {
            if (currentState.shouldLoadNewImage) {
                try {
                    val image = interactor.getRandomImage()
                    updateState(currentState.copy(image = image, shouldLoadNewImage = false))
                } catch (e: Exception) {
                    e.printStackTrace()
                    publishLabel(RandomImageLabel.MessageImageLoadingError)
                }
            }
        }
    }

    override fun onEvent(event: RandomImageEvent) {
        when (event) {
            is RandomImageEvent.SaveUnsplashImage -> saveUnsplashImage(event.image)
        }
    }

    private fun saveUnsplashImage(image: UnsplashImage) {
        launch {
            try {
                interactor.saveUnsplashImage(image)
                publishLabel(RandomImageLabel.MessageImageSaved)
                publishLabel(RandomImageLabel.Back)
            } catch (e: Exception) {
                e.printStackTrace()
                publishLabel(RandomImageLabel.MessageImageSaveError)
            }
        }
    }
}