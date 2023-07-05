package com.ksayker.modiface.domain.entity.filter

import android.graphics.Bitmap
import android.graphics.Color
import java.util.Random

data class NoiseFilter(
    private val representationColor: Int,
    private val representationDescription: String
) : Filter {

    override fun getRepresentationColor() = representationColor

    override fun getRepresentationDescription() = representationDescription

    override fun apply(bitmap: Bitmap): Bitmap {
        val colorMax = 0xFF
        val width: Int = bitmap.width
        val height: Int = bitmap.height
        val pixels = IntArray(width * height)
        val random = Random()

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        var index = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                index = y * width + x
                val randColor = Color.rgb(
                    random.nextInt(colorMax),
                    random.nextInt(colorMax),
                    random.nextInt(colorMax)
                )
                pixels[index] = pixels[index] or randColor
            }
        }
        val result = Bitmap.createBitmap(width, height, bitmap.config)
        result.setPixels(pixels, 0, width, 0, 0, width, height)

        bitmap.recycle()

        return result
    }
}