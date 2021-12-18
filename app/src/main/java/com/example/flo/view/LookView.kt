package com.example.flo.view

import com.example.flo.data.Song

interface LookView {
    fun onGetSongsLoading()
    fun onGetSongsSuccess(songs : ArrayList<Song>)
    fun onGetSongsFailure(code : Int, message : String)
}