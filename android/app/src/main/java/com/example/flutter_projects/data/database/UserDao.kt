package com.example.flutter_projects.data.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flutter_projects.domain.db.UserDatabaseModel

@androidx.room.Dao
interface UserDao {
    @Query("SELECT * FROM UserDatabaseModel ORDER BY created_on ASC")
    suspend fun getAll(): List<UserDatabaseModel>

    @Query("SELECT * FROM UserDatabaseModel where id =:id")
    suspend fun getUserById(id: Int): UserDatabaseModel?

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insert(notes: UserDatabaseModel)

    @Update
    suspend fun update(note: UserDatabaseModel)

    @Query("DELETE FROM UserDatabaseModel WHERE id = :id")
    suspend fun delete(id: Int)

}
