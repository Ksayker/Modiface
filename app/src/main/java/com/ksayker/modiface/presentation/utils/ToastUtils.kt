package com.ksayker.modiface.presentation.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(@StringRes messageId: Int) = showToast(getString(messageId))

fun Context.showToast(message: String) {
    fun showToast() {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    if (Looper.myLooper() == null) {
        Handler(Looper.getMainLooper()).post { showToast() }
    } else {
        showToast()
    }
}