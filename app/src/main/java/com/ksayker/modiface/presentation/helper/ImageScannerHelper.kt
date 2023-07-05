package com.ksayker.modiface.presentation.helper

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import com.ksayker.modiface.core.annotation.AppContext
import javax.inject.Inject

private const val TYPE = "image/*"

class ImageScannerHelper @Inject constructor(
    @AppContext private val context: Context
) {
    fun scanImage(pathToScan: String, block: (uri: Uri) -> Unit) {
        val paths = arrayOf(pathToScan)
        val mimeTypes = arrayOf(TYPE)
        MediaScannerConnection.scanFile(context.applicationContext, paths, mimeTypes) { _, uri ->
            block(uri)
        }
    }
}