package com.example.flutter_projects.domain


import android.graphics.Picture
import android.net.Uri


data class User(
    var name: String,
    var address: String,
    var phoneNumber: String,
    var profilePic: Uri?,
    var path: Picture?,
    var signaturePath:Uri?
)