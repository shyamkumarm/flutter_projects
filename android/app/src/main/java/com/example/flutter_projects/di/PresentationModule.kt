package com.example.flutter_projects.di

import com.example.flutter_projects.presentation.MyUserDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val requestViewModel = module {
    viewModel { MyUserDataViewModel(get()) }
}

/*private val appModule = module {
    single { } // app utils if any

}*/

val presentationModules = listOf(requestViewModel)