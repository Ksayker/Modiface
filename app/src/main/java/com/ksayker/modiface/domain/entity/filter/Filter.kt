package com.ksayker.modiface.domain.entity.filter

import android.graphics.Bitmap

interface Filter {

    fun getRepresentationColor(): Int

    fun getRepresentationDescription(): String

    fun apply(bitmap: Bitmap): Bitmap
}