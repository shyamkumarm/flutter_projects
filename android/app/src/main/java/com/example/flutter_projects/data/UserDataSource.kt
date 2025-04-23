package com.example.flutter_projects.data

import android.content.Context
import com.example.flutter_projects.domain.User

class UserDataSource(private val application: Context) {


    fun saveDataSource(data:User): Result<User> {
        return Result.runCatching {
            data
        }
    }
}