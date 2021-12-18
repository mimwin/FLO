package com.example.flo.album_viewpager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.`interface`.SongService
import com.example.flo.adapter.FragmentSongRecyclerAdapter
import com.example.flo.adapter.getAlbumIndex
import com.example.flo.databinding.FragmentSongBinding
import com.example.flo.response.AlbumIdx
import com.example.flo.view.AlbumIdxView

class SongFragment : Fragment(),AlbumIdxView {

    lateinit var binding : FragmentSongBinding
    var isToggle : Boolean = true
    private lateinit var fragmentSongRecyclerAdapter: FragmentSongRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSongBinding.inflate(inflater,container,false)
        binding.v1MixToggle.setOnClickListener{ setToggleStatus(isToggle)}
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        getAlbumIdx()
    }

    private fun getAlbumIdx() {
        val songService = SongService()
        songService.setAlbumIdxView(this)

        Log.e("GETALBUMINDEX", getAlbumIndex(requireContext()).toString())
        songService.getAlbumIdx(getAlbumIndex(requireContext()))
    }

    private fun initRecyclerView(){
        fragmentSongRecyclerAdapter = FragmentSongRecyclerAdapter()
        binding.v1LatestalbumRecyclerview.adapter = fragmentSongRecyclerAdapter
    }

    fun setToggleStatus(istoggle : Boolean){
        if(istoggle){
            isToggle=false
            binding.v1MixToggle.setImageResource(R.drawable.btn_toggle_on)
        }else{
            isToggle = true
            binding.v1MixToggle.setImageResource(R.drawable.btn_toggle_off)
        }
    }

    override fun onGetAlbumsIdxLoading() {
        binding.v1LoadingPb.visibility = View.VISIBLE
    }

    override fun onGetAlbumsIdxSuccess(albumIdx: ArrayList<AlbumIdx>) {
        binding.v1LoadingPb.visibility = View.GONE

        fragmentSongRecyclerAdapter.addSongs(albumIdx)
    }

    override fun onGetAlbumsIdxFailure(code: Int, message: String) {
        binding.v1LoadingPb.visibility = View.GONE

        when(code){
            400 -> Log.d("LOOKFRAG/API",message)
        }
    }
}