package com.ksayker.modiface.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import com.ksayker.modiface.presentation.utils.atLeastP

fun Context.getBitmap(uri: String): Bitmap? = getBitmap(Uri.parse(uri))

fun Context.getBitmap(uri: Uri): Bitmap? = try {
    if (atLeastP()) {
        val source = ImageDecoder.createSource(contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    } else {
        MediaStore.Images.Media.getBitmap(contentResolver, uri)
    }
} catch (e: Exception) {
    null
}
