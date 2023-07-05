package com.ksayker.modiface.presentation.helper

import android.graphics.Bitmap
import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.domain.provider.DispatcherProvider
import com.ksayker.modiface.domain.source.FileSource
import com.ksayker.modiface.domain.source.ImageSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class BitmapSaveHelper @Inject constructor(
    private val fileSource: FileSource,
    private val imageSource: ImageSource,
    private val imageScannerHelper: ImageScannerHelper,
    private val dispatcherProvider: DispatcherProvider
) {

    fun saveBitmap(bitmap: Bitmap, onSuccess: () -> Unit) {
        val scope = CoroutineScope(dispatcherProvider.defaultDispatcher)

        scope.launch {
            val pathToFile = fileSource.saveBitmap(bitmap)

            imageScannerHelper.scanImage(pathToFile) { uri ->
                scope.launch {
                    imageSource.addNewImage(Image(uri.toString()))
                }
            }
            onSuccess()
        }
    }
}