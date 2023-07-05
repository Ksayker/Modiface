package com.ksayker.modiface.presentation.helper


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ImageFormat
import android.graphics.Rect
import android.media.Image
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicYuvToRGB
import com.ksayker.modiface.core.annotation.AppContext
import java.nio.ByteBuffer
import javax.inject.Inject


/**
 * Helper class used to efficiently convert a [Media.Image] object from
 * YUV_420_888 or JPEG format to an RGB [Bitmap] object.
 *
 * Copied from https://github.com/owahltinez/camerax-tflite/blob/master/app/src/main/java/com/android/example/camerax/tflite/YuvToRgbConverter.kt
 *
 * The [yuvOrJpegToRgb] method is able to achieve the same FPS as the CameraX image
 * analysis use case at the default analyzer resolution, which is 30 FPS with
 * 640x480 on a Pixel 3 XL device.
 */
// TODO: refactor
class ImageConverterHelper @Inject constructor(
    @AppContext context: Context
) {
    private val rs = RenderScript.create(context)
    private val scriptYuvToRgb = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs))

    private var pixelCount: Int = -1
    private lateinit var yuvBuffer: ByteBuffer
    private lateinit var inputAllocation: Allocation
    private lateinit var outputAllocation: Allocation

    @Synchronized
    fun yuvOrJpegToRgb(image: Image, output: Bitmap) {

        // Ensure that the intermediate output byte buffer is allocated
        if (!::yuvBuffer.isInitialized) {
            pixelCount = image.cropRect.width() * image.cropRect.height()
            yuvBuffer = ByteBuffer.allocateDirect(
                pixelCount * ImageFormat.getBitsPerPixel(ImageFormat.YUV_420_888) / 8
            )
        }

        when {
            image.format == ImageFormat.YUV_420_888 -> {
                // Get the YUV data in byte array form
                imageToByteBuffer(image, yuvBuffer)

                // Ensure that the RenderScript inputs and outputs are allocated
                if (!::inputAllocation.isInitialized) {
                    inputAllocation = Allocation.createSized(rs, Element.U8(rs), yuvBuffer.array().size)
                }
                if (!::outputAllocation.isInitialized) {
                    outputAllocation = Allocation.createFromBitmap(rs, output)
                }

                // Convert YUV to RGB
                inputAllocation.copyFrom(yuvBuffer.array())
                scriptYuvToRgb.setInput(inputAllocation)
                scriptYuvToRgb.forEach(outputAllocation)
                outputAllocation.copyTo(output)
            }

            image.format == ImageFormat.JPEG -> {
                val buffer = image.planes[0].buffer
                val bytes = ByteArray(buffer.capacity())
                buffer[bytes]
                val bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)

                val canvas = Canvas(output)
                canvas.drawBitmap(bitmapImage, 0f, 0f, null)
            }
        }
    }

    private fun imageToByteBuffer(image: Image, outputBuffer: ByteBuffer) {
        assert(image.format == ImageFormat.YUV_420_888)

        val imageCrop = image.cropRect
        val imagePlanes = image.planes
        val rowData = ByteArray(imagePlanes.first().rowStride)

        imagePlanes.forEachIndexed { planeIndex, plane ->

            // How many values are read in input for each output value written
            // Only the Y plane has a value for every pixel, U and V have half the resolution i.e.
            //
            // Y Plane            U Plane    V Plane
            // ===============    =======    =======
            // Y Y Y Y Y Y Y Y    U U U U    V V V V
            // Y Y Y Y Y Y Y Y    U U U U    V V V V
            // Y Y Y Y Y Y Y Y    U U U U    V V V V
            // Y Y Y Y Y Y Y Y    U U U U    V V V V
            // Y Y Y Y Y Y Y Y
            // Y Y Y Y Y Y Y Y
            // Y Y Y Y Y Y Y Y
            val outputStride: Int

            // The index in the output buffer the next value will be written at
            // For Y it's zero, for U and V we start at the end of Y and interleave them i.e.
            //
            // First chunk        Second chunk
            // ===============    ===============
            // Y Y Y Y Y Y Y Y    U V U V U V U V
            // Y Y Y Y Y Y Y Y    U V U V U V U V
            // Y Y Y Y Y Y Y Y    U V U V U V U V
            // Y Y Y Y Y Y Y Y    U V U V U V U V
            // Y Y Y Y Y Y Y Y
            // Y Y Y Y Y Y Y Y
            // Y Y Y Y Y Y Y Y
            var outputOffset: Int

            when (planeIndex) {
                0 -> {
                    outputStride = 1
                    outputOffset = 0
                }

                1 -> {
                    outputStride = 2
                    outputOffset = pixelCount + 1
                }

                2 -> {
                    outputStride = 2
                    outputOffset = pixelCount
                }

                else -> {
                    // Image contains more than 3 planes, something strange is going on
                    return@forEachIndexed
                }
            }

            val buffer = plane.buffer
            val rowStride = plane.rowStride
            val pixelStride = plane.pixelStride

            // We have to divide the width and height by two if it's not the Y plane
            val planeCrop = if (planeIndex == 0) {
                imageCrop
            } else {
                Rect(
                    imageCrop.left / 2,
                    imageCrop.top / 2,
                    imageCrop.right / 2,
                    imageCrop.bottom / 2
                )
            }

            val planeWidth = planeCrop.width()
            val planeHeight = planeCrop.height()

            buffer.position(rowStride * planeCrop.top + pixelStride * planeCrop.left)
            for (row in 0 until planeHeight) {
                val length: Int
                if (pixelStride == 1 && outputStride == 1) {
                    // When there is a single stride value for pixel and output, we can just copy
                    // the entire row in a single step
                    length = planeWidth
                    buffer.get(outputBuffer.array(), outputOffset, length)
                    outputOffset += length
                } else {
                    // When either pixel or output have a stride > 1 we must copy pixel by pixel
                    length = (planeWidth - 1) * pixelStride + 1
                    buffer.get(rowData, 0, length)
                    for (col in 0 until planeWidth) {
                        outputBuffer.array()[outputOffset] = rowData[col * pixelStride]
                        outputOffset += outputStride
                    }
                }

                if (row < planeHeight - 1) {
                    buffer.position(buffer.position() + rowStride - length)
                }
            }
        }
    }
}