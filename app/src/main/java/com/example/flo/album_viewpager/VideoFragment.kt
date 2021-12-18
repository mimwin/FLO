package com.example.flo.album_viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.Song
import com.example.flo.adapter.VideoFragmentRecyclerAdapter
import com.example.flo.databinding.FragmentSongVideoBinding

class VideoFragment : Fragment() {
    lateinit var binding : FragmentSongVideoBinding
    lateinit var RecyclerViewAdapter : VideoFragmentRecyclerAdapter
    private val songarr = arrayListOf<Song>(
        Song("[MV] Coin","아이유 (IU)"),
        Song("[MV] 라일락 (LILAC)","아이유 (IU)"),
        Song("[티저] 라일락 (LILAC)","아이유 (IU)")
    )
    lateinit var recycleview : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSongVideoBinding.inflate(inflater,container,false)

        recycleview = binding.v3Listview
        recycleview.layoutManager = LinearLayoutManager(context)

        RecyclerViewAdapter = VideoFragmentRecyclerAdapter(songarr)
        recycleview.adapter = RecyclerViewAdapter

        return binding.root
    }
}