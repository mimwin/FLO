package com.example.flo.data

import androidx.room.Entity
import androidx.room.PrimaryKey


//제목, 가수, 사진,재생시간,현재 재생시간, isplaying(재생 되고 있는지)
@Entity(tableName = "SongTable")
data class Song(
        var title: String = "",
        var singer: String = "",
        var second: Int = 0,
        var playTime: Int = 0,
        var isPlaying: Boolean = false,
        var music: String = "",
        var coverImg: Int? = null,
        var coverImgUrl: String? = null,
        var isLike: Boolean = false,
        var albumIdx: Int = 0 // 이 song이 어떤 앨범에 담겨 있는지 가리키는 변수 (foreign key 역할)
){
        @PrimaryKey(autoGenerate = true) var id: Int = 0
}
