package com.example.flo.`interface`

import android.util.Log
import com.example.flo.getRetrofit
import com.example.flo.response.AlbumIdxResponse
import com.example.flo.response.AlbumResponse
import com.example.flo.response.SongResponse
import com.example.flo.view.AlbumIdxView
import com.example.flo.view.HomeLatestView
import com.example.flo.view.LookView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//AuthService와 비슷한 작업
class SongService {
    private lateinit var lookView : LookView
    private lateinit var homeLatestView: HomeLatestView
    private lateinit var albumIdxView : AlbumIdxView

    private val SongService = getRetrofit().create(SongRetrofitInterface::class.java)

    fun setLookView(lookView : LookView){
        this.lookView = lookView
    }

    fun setHomeAlbumView(homeLatestView: HomeLatestView){
        this.homeLatestView = homeLatestView
    }

    fun setAlbumIdxView(albumIdxView: AlbumIdxView){
        this.albumIdxView = albumIdxView
    }

    fun getSongs(){

        lookView.onGetSongsLoading()

        SongService.getSongs().enqueue(object: Callback<SongResponse> {
            override fun onResponse(call: Call<SongResponse>, response: Response<SongResponse>) {
                val SongResponse: SongResponse = response.body()!!

                Log.e("SignUP/API",SongResponse.toString())

                when(SongResponse.code){
                    1000 -> lookView.onGetSongsSuccess(SongResponse.result!!.songs)
                    else -> lookView.onGetSongsFailure(SongResponse.code, SongResponse.message)
                }
            }

            override fun onFailure(call: Call<SongResponse>, t: Throwable) {
                lookView.onGetSongsFailure(400, "네트워크 오류가 발생했습니다.")
            }

        })
    }

    fun getAlbums(){

        homeLatestView.onGetAlbumsLoading()

        SongService.getAlbums().enqueue(object: Callback<AlbumResponse> {
            override fun onResponse(call: Call<AlbumResponse>, response: Response<AlbumResponse>) {
                val AlbumResponse: AlbumResponse = response.body()!!

                Log.e("GETALBUMS/API",AlbumResponse.toString())

                when(AlbumResponse.code){
                    1000 -> homeLatestView.onGetAlbumsSuccess(AlbumResponse.result!!.albums)
                    else -> homeLatestView.onGetAlbumsFailure(AlbumResponse.code, AlbumResponse.message)
                }
            }

            override fun onFailure(call: Call<AlbumResponse>, t: Throwable) {
                homeLatestView.onGetAlbumsFailure(400, "네트워크 오류가 발생했습니다.")
            }

        })
    }

    fun getAlbumIdx(albumIdx : Int){

        albumIdxView.onGetAlbumsIdxLoading()

        SongService.getAlbumIdx(albumIdx).enqueue(object: Callback<AlbumIdxResponse> {
            override fun onResponse(call: Call<AlbumIdxResponse>, response: Response<AlbumIdxResponse>) {
                val AlbumIdxResponse: AlbumIdxResponse = response.body()!!

                Log.e("GETALBUMIDX/API",AlbumIdxResponse.result.toString())

                when(AlbumIdxResponse.code){
                    1000 -> albumIdxView.onGetAlbumsIdxSuccess(AlbumIdxResponse.result!!)
                    else -> albumIdxView.onGetAlbumsIdxFailure(AlbumIdxResponse.code, AlbumIdxResponse.message)
                }
            }

            override fun onFailure(call: Call<AlbumIdxResponse>, t: Throwable) {
                albumIdxView.onGetAlbumsIdxFailure(400, "네트워크 오류가 발생했습니다.")
            }

        })
    }
}