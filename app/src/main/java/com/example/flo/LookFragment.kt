package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.`interface`.SongService
import com.example.flo.adapter.LookChartRecyclerAdapter
import com.example.flo.data.Song
import com.example.flo.databinding.FragmentLookBinding
import com.example.flo.view.LookView


class LookFragment : Fragment(), LookView {

    private lateinit var binding: FragmentLookBinding
    private lateinit var lookChartAdpater : LookChartRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initRecyclerView()
        getSongs()
    }

    private fun initRecyclerView(){
        lookChartAdpater = LookChartRecyclerAdapter(requireContext())
        binding.lookChartRecyclerview.adapter = lookChartAdpater
    }

    private fun getSongs(){
        val songService = SongService()
        songService.setLookView(this)

        songService.getSongs()
    }

    override fun onGetSongsLoading() {
        binding.lookLoadingPb.visibility = View.VISIBLE
    }

    override fun onGetSongsSuccess(songs : ArrayList<Song>) {
        binding.lookLoadingPb.visibility = View.GONE

        lookChartAdpater.addSongs(songs)
    }

    override fun onGetSongsFailure(code: Int, message: String) {
        binding.lookLoadingPb.visibility = View.GONE

        when(code){
            400 -> Log.d("LOOKFRAG/API",message)
        }
    }

}