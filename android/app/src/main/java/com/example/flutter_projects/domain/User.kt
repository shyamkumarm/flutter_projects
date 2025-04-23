package com.example.flutter_projects.domain

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val name: String,
    val address: String,
    val phoneNumber: Long,
    val profilePic: String,
)