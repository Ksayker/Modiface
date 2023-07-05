package com.ksayker.modiface.domain.interactor.editimage

import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.domain.entity.filter.Filter
import com.ksayker.modiface.domain.provider.DispatcherProvider
import com.ksayker.modiface.domain.source.FileSource
import com.ksayker.modiface.domain.source.FilterSource
import com.ksayker.modiface.domain.source.ImageSource
import com.ksayker.modiface.presentation.helper.ImageScannerHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditImageInteractorImpl @Inject constructor(
    private val filterSource: FilterSource,
    private val fileSource: FileSource,
    private val imageSource: ImageSource,
    private val imageScannerHelper: ImageScannerHelper,
    private val dispatcherProvider: DispatcherProvider
) : EditImageInteractor {

    override suspend fun getFilters() = withContext(dispatcherProvider.defaultDispatcher) {
        filterSource.getFilters()
    }

    override suspend fun saveImage(filter: Filter, image: Image): Unit =
        withContext(dispatcherProvider.ioDispatcher) {
            val pathToFile = fileSource.saveImage(filter, image)
            val scope = CoroutineScope(Dispatchers.Default)

            imageScannerHelper.scanImage(pathToFile) { uri ->
                scope.launch {
                    imageSource.addNewImage(Image(uri.toString()))
                }
            }
        }
}