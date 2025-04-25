package com.example.flutter_projects.domain


import android.graphics.Picture


data class User(
    var name: String,
    var address: String,
    var phoneNumber: String,
    var profilePic: String?,
    var path: Picture?,
    var signaturePath: String?
)