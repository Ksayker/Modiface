package com.ksayker.modiface.domain.entity.filter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.LightingColorFilter
import android.graphics.Paint

data class TintFilter(
    private val color: Int,
    private val description: String
) : Filter {

    override fun getRepresentationColor() = color

    override fun getRepresentationDescription() = description

    override fun apply(bitmap: Bitmap): Bitmap {
        val width: Int = bitmap.width
        val height: Int = bitmap.height

        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val paint = Paint()
        val filter: ColorFilter = LightingColorFilter(color, 1)
        paint.colorFilter = filter

        val bitmapBuff = bitmap.copy(Bitmap.Config.ARGB_8888, false)
        val canvas = Canvas()
        canvas.setBitmap(result)
        canvas.drawBitmap(bitmapBuff, 0f, 0f, paint)

        bitmap.recycle()

        return result
    }
}