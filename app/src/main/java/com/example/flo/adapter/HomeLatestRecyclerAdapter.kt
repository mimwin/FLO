package com.example.flo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flo.data.Album
import com.example.flo.databinding.ItemHomeLatestsongBinding

class HomeLatestRecyclerAdapter(val context : Context) : RecyclerView.Adapter<HomeLatestRecyclerAdapter.viewHolder>() {
    private val albums = ArrayList<Album>()

    //클릭 인터페이스 정의
    interface MyItemClickListener{
        fun onItemClick(album : Album, position : Int)
    }

    //리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeLatestRecyclerAdapter.viewHolder {
        val binding : ItemHomeLatestsongBinding = ItemHomeLatestsongBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeLatestRecyclerAdapter.viewHolder, position: Int) {

        holder.bind(albums[position])
        holder.itemView.setOnClickListener {
            Log.e("HOMELATEST",position.toString())
            mItemClickListener.onItemClick(albums[position],position)
        }

    }

    override fun getItemCount(): Int = albums.size

    //뷰홀더
    inner class viewHolder(val binding: ItemHomeLatestsongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (homealbum : Album){
            if(homealbum.coverImgUrl==""){
                Glide.with(context).load(homealbum.coverImg!!).into(binding.itemLatestsongIv)
            }else{
                Glide.with(context).load(homealbum.coverImgUrl).into(binding.itemLatestsongIv)
            }
            binding.itemLatestsongTitle.text = homealbum.title
            binding.itemLatestsongSinger.text = homealbum.singer
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAlbums(albums: ArrayList<Album>) {
        this.albums.clear()
        this.albums.addAll(albums)

        notifyDataSetChanged()
    }
}