package com.example.qrcodeexample.photo.model

import android.net.Uri

data class Photo(
    val id: Long,
    val uri: Uri,
    val name: String,
    val date: Long
)