package com.example.flutter_projects.domain

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val name: String,
    val address: String,
    val phoneNumber: Number,
    val uri: Uri,
    val profilePic: String,
)