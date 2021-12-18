package com.example.flo.view

import com.example.flo.data.Album

interface HomeLatestView {
    fun onGetAlbumsLoading()
    fun onGetAlbumsSuccess(albums : ArrayList<Album>)
    fun onGetAlbumsFailure(code : Int, message : String)
}