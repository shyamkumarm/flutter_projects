package com.example.flutter_projects.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserDataCase(private val mediaRepo: IUserData) {
    fun invoke(user: User) = flow {
        val savedResult = mediaRepo.saveUserData(user = user).fold(
            onSuccess = { user ->
                user
            }, onFailure = { exception ->
               exception.message ?: "Some thing wrong with database"
            })
        emit(savedResult)
    }.flowOn(Dispatchers.IO)

}