package com.example.flutter_projects.domain.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity
data class UserDatabaseModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "profilePic") val profilePic: String,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: Long,
    @ColumnInfo(name = "created_on") val createdOn: Date,
) {
    fun getFormat(): String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(createdOn)
}