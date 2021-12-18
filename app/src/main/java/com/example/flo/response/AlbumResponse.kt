package com.example.flo.response

import com.example.flo.data.Album
import com.google.gson.annotations.SerializedName

data class Albums(
        @SerializedName("albums") val albums : ArrayList<Album>
)

data class AlbumResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code : Int,
        @SerializedName("message") val message : String,
        @SerializedName("result") val result : Albums?
)