package com.example.flo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemFragmentSongBinding
import com.example.flo.response.AlbumIdx

class FragmentSongRecyclerAdapter() : RecyclerView.Adapter<FragmentSongRecyclerAdapter.viewHolder>() {
    val songs = ArrayList<AlbumIdx>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentSongRecyclerAdapter.viewHolder {
        val binding : ItemFragmentSongBinding = ItemFragmentSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: FragmentSongRecyclerAdapter.viewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int = songs.size

    inner class viewHolder(val binding : ItemFragmentSongBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(albumIdx : AlbumIdx){
            binding.fragSongTitle.text = albumIdx.title
            binding.fragSongSinger.text = albumIdx.singer
            val ranking : Int = albumIdx.songIdx
            if(ranking<10){
                binding.fragSongRankingTv.text = "0"+ranking.toString()
            }else{
                binding.fragSongRankingTv.text = ranking.toString()
            }
            if(albumIdx.isTitleSong == "T"){
                binding.fragSongIsmainTv.visibility = View.VISIBLE
            }else{
                binding.fragSongIsmainTv.visibility = View.GONE
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(albumIdx: ArrayList<AlbumIdx>) {
        this.songs.clear()
        this.songs.addAll(albumIdx)

        notifyDataSetChanged()
    }
}