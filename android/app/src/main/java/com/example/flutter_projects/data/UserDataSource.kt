package com.example.flutter_projects.data

import com.example.flutter_projects.data.database.UserDao
import com.example.flutter_projects.domain.User
import com.example.flutter_projects.domain.db.UserDatabaseModel
import java.util.Date

class UserDataSource(private val userDao: UserDao) {


    suspend fun saveDataSource(data: User): Result<User> {
        return try {

            userDao.insert(
                UserDatabaseModel(
                    name = data.name, address = data.address, phoneNumber = data.phoneNumber, profilePic =
                        data.profilePic.toString(), signaturePic = data.signaturePath.toString() ,createdOn = Date(System.currentTimeMillis())
                )
            )
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}