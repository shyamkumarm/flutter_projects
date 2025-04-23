package com.example.flutter_projects

import android.app.Application
import com.example.flutter_projects.di.dataModules
import com.example.flutter_projects.di.domainModules
import com.gallery.myapplication.di.presentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(presentationModules + domainModules + dataModules)
        }
    }
}