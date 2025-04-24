package com.example.flutter_projects.di

import com.example.flutter_projects.domain.UserDataCase
import org.koin.dsl.module


private val domainModule = module {
    single { UserDataCase(get(), get()) }

}

val domainModules = listOf(domainModule)