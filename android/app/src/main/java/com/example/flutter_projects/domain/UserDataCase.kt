package com.example.flutter_projects.domain

import android.content.Context
import com.example.flutter_projects.utils.AppUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class UserDataCase(private val mediaRepo: IUserData, private val context: Context) {
    fun invoke(user: User) = flow {
        withContext(Dispatchers.IO) {
            user.path?.let {
                user.signaturePath = AppUtils.saveBitmapToFile(context,it)
            }
        }
        val savedResult = mediaRepo.saveUserData(user = user).fold(
            onSuccess = { user ->
                user
            }, onFailure = { exception ->
                exception.message ?: "Some thing wrong with database"
            })
        emit(savedResult)
    }.flowOn(Dispatchers.IO)



    fun getUser() = flow {
        val savedResult = mediaRepo.getUser().fold(
            onSuccess = { userList ->
                userList
            }, onFailure = { exception ->
                exception.message ?: "Some thing wrong with database"
            })
        emit(savedResult)
    }.flowOn(Dispatchers.IO)

}