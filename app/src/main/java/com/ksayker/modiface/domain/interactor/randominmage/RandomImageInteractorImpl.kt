package com.ksayker.modiface.domain.interactor.randominmage

import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.domain.entity.UnsplashImage
import com.ksayker.modiface.domain.provider.DispatcherProvider
import com.ksayker.modiface.domain.source.FileSource
import com.ksayker.modiface.domain.source.ImageSource
import com.ksayker.modiface.domain.source.UnsplashSource
import com.ksayker.modiface.presentation.helper.ImageScannerHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RandomImageInteractorImpl @Inject constructor(
    private val imageSource: ImageSource,
    private val fileSource: FileSource,
    private val unsplashSource: UnsplashSource,
    private val imageScannerHelper: ImageScannerHelper,
    private val dispatcherProvider: DispatcherProvider
) : RandomImageInteractor {

    override suspend fun saveUnsplashImage(image: UnsplashImage) {
        withContext(dispatcherProvider.ioDispatcher) {
            val pathToFile = fileSource.saveUnsplashImage(image)
            val scope = CoroutineScope(Dispatchers.Default)

            imageScannerHelper.scanImage(pathToFile) { uri ->
                scope.launch {
                    imageSource.addNewImage(Image(uri.toString()))
                }
            }
        }
    }

    override suspend fun getRandomImage() = unsplashSource.getRandomImage()
}