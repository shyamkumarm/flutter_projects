package com.example.flutter_projects.domain

interface IUserData {

    suspend fun saveUserData(user: User): Result<User>
}