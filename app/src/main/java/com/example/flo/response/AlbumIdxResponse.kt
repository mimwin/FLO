package com.example.flo.response

import com.google.gson.annotations.SerializedName

data class AlbumIdx(
        @SerializedName("songIdx") val songIdx : Int,
        @SerializedName("title") val title : String,
        @SerializedName("singer") val singer : String,
        @SerializedName("isTitleSong") val isTitleSong : String
)

data class AlbumIdxResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code : Int,
        @SerializedName("message") val message : String,
        @SerializedName("result") val result : ArrayList<AlbumIdx>?
)