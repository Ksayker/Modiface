package com.ksayker.modiface.presentation.utils

import android.os.Build

fun atLeastP() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

fun atLeastR() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

fun atLeastTiramisu() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
