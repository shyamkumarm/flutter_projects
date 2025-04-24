package com.example.flutter_projects.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flutter_projects.domain.db.UserDatabaseModel

const val version = 1

@Database(entities = [UserDatabaseModel::class], version = version, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserDataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}
