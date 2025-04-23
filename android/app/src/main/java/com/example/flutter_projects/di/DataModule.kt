package com.example.flutter_projects.di

import com.example.flutter_projects.data.UserDataSource
import com.example.flutter_projects.data.UserRepo
import com.example.flutter_projects.domain.IUserData
import org.koin.dsl.module


val mediaModules = module {
    single { UserDataSource(get()) }
    single { getUserRepository(get<UserDataSource>()) }
}

fun getUserRepository(remoteDataSource: UserDataSource): IUserData = UserRepo(remoteDataSource)

val dataModules = listOf(mediaModules)