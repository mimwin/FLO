package com.example.flo.view

import com.example.flo.response.AlbumIdx

interface AlbumIdxView {
    fun onGetAlbumsIdxLoading()
    fun onGetAlbumsIdxSuccess(albumIdx : ArrayList<AlbumIdx>)
    fun onGetAlbumsIdxFailure(code : Int, message : String)
}