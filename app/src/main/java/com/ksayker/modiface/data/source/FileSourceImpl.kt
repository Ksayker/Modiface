package com.ksayker.modiface.data.source

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import com.ksayker.modiface.core.annotation.AppContext
import com.ksayker.modiface.core.utils.getBitmap
import com.ksayker.modiface.data.remote.UnsplashApiService
import com.ksayker.modiface.domain.entity.Image
import com.ksayker.modiface.domain.entity.UnsplashImage
import com.ksayker.modiface.domain.entity.filter.Filter
import com.ksayker.modiface.domain.provider.DispatcherProvider
import com.ksayker.modiface.domain.source.FileSource
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.BufferedInputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


private const val DATE_FORMAT = "yyyymmsshhmmss"

private const val DIR = "Modiface"

class FileSourceImpl @Inject constructor(
    @AppContext private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val unsplashApiService: UnsplashApiService
) : FileSource {

    override suspend fun saveImage(filter: Filter, image: Image): String {
        val bitmap = getBitmap(image)

        return if (bitmap != null) {
            val newBitmap = filter.apply(bitmap)
            saveBitmap(newBitmap)
        } else {
            ""
        }
    }

    override suspend fun saveBitmap(bitmap: Bitmap): String = withContext(dispatcherProvider.ioDispatcher) {
        var pathToFile = ""
        val newFile = File(createPathToFile())

        try {
            val fileOutPutStream = FileOutputStream(newFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutPutStream)
            fileOutPutStream.flush()
            fileOutPutStream.close()

            pathToFile = newFile.absolutePath
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        pathToFile
    }

    // TODO: refactor nullable
    override suspend fun saveUnsplashImage(image: UnsplashImage): String = image.urls?.regular?.let { url ->
        val responseBody = unsplashApiService.downloadImage(url)

        saveResponseBodyToDisk(responseBody)
    }
        ?: ""

    private fun saveResponseBodyToDisk(body: ResponseBody): String {
        val newFile = File(createPathToFile())

        val bufferedInputStream = BufferedInputStream(body.byteStream())
        val outputStream: OutputStream = FileOutputStream(newFile)
        val buffer = ByteArray(1024)

        var bytesRead: Int
        while (bufferedInputStream.read(buffer).also { bytesRead = it } != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }

        outputStream.flush()
        outputStream.close()

        return newFile.absolutePath
    }

    @SuppressLint("SimpleDateFormat")
    private fun createPathToFile(): String {
        val dir = getDirectory()
        if (!dir.exists() && !dir.mkdirs()) {
            dir.mkdir()
        }

        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT)
        val date = simpleDateFormat.format(Date())
        val name = "IMG$date.jpg"

        return dir.absolutePath + "/" + name
    }

    private fun getBitmap(image: Image) = context.getBitmap(image.uri)

    private fun getDirectory(): File {
        val file = Environment.getExternalStorageDirectory()
        return File(file, DIR)
    }
}