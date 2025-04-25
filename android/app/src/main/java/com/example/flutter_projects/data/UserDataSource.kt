package com.example.flutter_projects.data

import com.example.flutter_projects.data.database.UserDao
import com.example.flutter_projects.domain.User
import com.example.flutter_projects.domain.UserItem
import com.example.flutter_projects.domain.db.UserDatabaseModel
import java.util.Date

class UserDataSource(private val userDao: UserDao) {


    suspend fun saveDataSource(data: User): Result<User> {
        return try {

            userDao.insert(
                UserDatabaseModel(
                    name = data.name,
                    address = data.address,
                    phoneNumber = data.phoneNumber,
                    profilePic =
                        data.profilePic ?: "",
                    signaturePic = data.signaturePath ?: "",
                    createdOn = Date(System.currentTimeMillis())
                )
            )
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveDataSource(): Result<UserItem> {
        return try {

            val userList = userDao.getAll(
            )
            Result.success(UserItem(userList))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun deleteDataSource(id: Int): Result<Int> {
        return try {
            userDao.delete(id)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}