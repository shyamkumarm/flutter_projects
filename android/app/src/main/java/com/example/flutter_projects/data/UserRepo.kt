package com.example.flutter_projects.data

import com.example.flutter_projects.domain.IUserData
import com.example.flutter_projects.domain.User

class UserRepo(private val source: UserDataSource) : IUserData {


    override suspend fun saveUserData(user: User): Result<User> {
        return source.saveDataSource(user)
    }
}
