package com.example.flutter_projects.di

import android.app.Application
import androidx.room.Room
import com.example.flutter_projects.data.UserDataSource
import com.example.flutter_projects.data.UserRepo
import com.example.flutter_projects.data.database.UserDao
import com.example.flutter_projects.data.database.UserDataBase
import com.example.flutter_projects.domain.IUserData
import org.koin.dsl.module

const val version = 1
const val DB_NAME = "user_database"
fun providesDatabase(application: Application): UserDataBase =
    Room.databaseBuilder(application, UserDataBase::class.java, DB_NAME)
        .build()


fun providesDao(db: UserDataBase): UserDao = db.getUserDao()


val roomModule = module {

    single { providesDatabase(get()) }
    single { providesDao(get()) }
}




val dataSrcModules = module {
    single { UserDataSource(get()) }
    single { getUserRepository(get<UserDataSource>()) }
}

fun getUserRepository(localDataSource: UserDataSource): IUserData = UserRepo(localDataSource)

val dataModules = listOf(dataSrcModules,roomModule)