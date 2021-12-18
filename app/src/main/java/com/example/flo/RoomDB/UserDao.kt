package com.example.flo.RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.flo.data.User

@Dao
interface UserDao {
    @Insert
    fun insert(user : User)

    @Query("SELECT * FROM UserTable")
    fun getUsers(): List<User>

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    fun getUser(email : String, password : String): User?

    @Query("SELECT * FROM UserTable WHERE id = :userid")
    fun getUserwithId(userid : Int) : User?
}