package com.example.flo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flo.data.Song
import com.example.flo.databinding.ItemLookChartBinding

class LookChartRecyclerAdapter(val context : Context) : RecyclerView.Adapter<LookChartRecyclerAdapter.viewHolder>() {
    private val songs = ArrayList<Song>()
    var cnt = 1

    //클릭 인터페이스 정의
    interface MyItemClickListener{
    }

    //리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
    private lateinit var mItemClickListener : LookChartRecyclerAdapter.MyItemClickListener

    fun setMyItemClickListener(itemClickListener: LookChartRecyclerAdapter.MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookChartRecyclerAdapter.viewHolder {
        val binding : ItemLookChartBinding = ItemLookChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: LookChartRecyclerAdapter.viewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.lookChartPlayIv.setOnClickListener {
            //플레이 버튼 클릭
        }
    }

    override fun getItemCount(): Int = songs.size

    inner class viewHolder(val binding : ItemLookChartBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(song : Song){
            if(song.coverImgUrl == ""){
                Glide.with(context).load(song.coverImg!!).into(binding.lookChartImage)
            }
            else{
                Glide.with(context).load(song.coverImgUrl).into(binding.lookChartImage)
            }
            binding.lookChartTitle.text = song.title
            binding.lookChartSinger.text = song.singer
            binding.lookChartRanking.text = (cnt++).toString()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }
}