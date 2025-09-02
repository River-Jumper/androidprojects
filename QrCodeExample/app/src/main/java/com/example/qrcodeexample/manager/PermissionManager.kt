package com.example.qrcodeexample.manager

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionManager(private val activity: ComponentActivity) {
    private var callback: ((Boolean) -> Unit)? = null

    private val requestPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted ->
            callback?.invoke(isGranted)
        }

    fun request(permission: String, onRequestPermissionResult: ((Boolean) -> Unit)) {
        val isGranted = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        if (isGranted) {
            onRequestPermissionResult(true)
        }
        else {
            callback = onRequestPermissionResult
            requestPermissionLauncher.launch(permission)
        }
    }
}