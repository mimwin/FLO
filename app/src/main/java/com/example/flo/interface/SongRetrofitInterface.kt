package com.example.flo.`interface`

import com.example.flo.response.AlbumIdxResponse
import com.example.flo.response.AlbumResponse
import com.example.flo.response.SongResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SongRetrofitInterface {

    @GET("/songs")
    fun getSongs(): Call<SongResponse>

    @GET("/albums")
    fun getAlbums(): Call<AlbumResponse>

    @GET("/albums/{albumIdx}")
    fun getAlbumIdx(@Path("albumIdx") albumIdx: Int): Call<AlbumIdxResponse>
}