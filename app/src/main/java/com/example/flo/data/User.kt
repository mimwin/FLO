package com.example.flo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserTable")
data class User(
        @SerializedName(value = "email") val email : String,
        @SerializedName(value = "password") val password : String,
        @SerializedName(value = "name") val name : String
){
        //유저가 늘어날때마다 id가 자동으로 증가한다.
        @PrimaryKey(autoGenerate = true)
        var id:Int = 0
}
