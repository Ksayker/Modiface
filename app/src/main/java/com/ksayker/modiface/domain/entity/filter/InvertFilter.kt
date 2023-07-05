package com.ksayker.modiface.domain.entity.filter

import android.graphics.Bitmap
import android.graphics.Color

data class InvertFilter(
    private val representationColor: Int,
    private val representationDescription: String
) : Filter {

    override fun getRepresentationColor() = representationColor

    override fun getRepresentationDescription() = representationDescription

    override fun apply(bitmap: Bitmap): Bitmap {
        val result = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        var alpha: Int
        var red: Int
        var green: Int
        var blue: Int
        var pixelColor: Int

        val height: Int = bitmap.height
        val width: Int = bitmap.width

        for (y in 0 until height) {
            for (x in 0 until width) {
                pixelColor = bitmap.getPixel(x, y)
                alpha = Color.alpha(pixelColor)
                red = 255 - Color.red(pixelColor)
                green = 255 - Color.green(pixelColor)
                blue = 255 - Color.blue(pixelColor)
                result.setPixel(x, y, Color.argb(alpha, red, green, blue))
            }
        }

        bitmap.recycle()

        return result
    }
}