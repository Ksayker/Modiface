package com.ksayker.modiface.presentation.helper

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import javax.inject.Inject

class PermissionHelper @Inject constructor() {

    private var requestPermissionLauncher: ActivityResultLauncher<Array<String>>? = null
    private var onGranted: (() -> Unit)? = null
    private var onShouldShowRational: (() -> Unit)? = null

    fun registerForActivityResult(fragment: Fragment) {
        val requestPermission = ActivityResultContracts.RequestMultiplePermissions()
        requestPermissionLauncher = fragment.registerForActivityResult(requestPermission) { granted ->
            if (granted.values.all { it }) {
                onGranted?.invoke()
                onGranted = null
            } else {
                onShouldShowRational?.invoke()
                onShouldShowRational = null
            }
        }
    }

    fun checkPermission(
        fragment: Fragment,
        permissions: Array<String>,
        onGranted: () -> Unit,
        onShouldShowRational: () -> Unit
    ) {
        this.onGranted = onGranted
        this.onShouldShowRational = onShouldShowRational

        val granted = PackageManager.PERMISSION_GRANTED
        val isGranted = permissions.all { ContextCompat.checkSelfPermission(fragment.requireContext(), it) == granted }
        val shouldShowRational = permissions.all { fragment.shouldShowRequestPermissionRationale(it) }

        when {
            isGranted -> onGranted()
            shouldShowRational -> onShouldShowRational()
            else -> askPermission(permissions)
        }
    }

    private fun askPermission(permissions: Array<String>) = requestPermissionLauncher?.launch(permissions)
}