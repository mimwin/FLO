package com.example.flo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.Album
import com.example.flo.databinding.ItemLockerSaveAlbumBinding

class LockerSaveAlbumRecyclerAdpater () : RecyclerView.Adapter<LockerSaveAlbumRecyclerAdpater.viewHolder>() {
    private val albums = ArrayList<Album>()

    //클릭 인터페이스 정의
    interface MyItemClickListener{
        fun onRemoveAlbum(position : Int)
    }

    //리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    // 뷰홀더를 생성해줄 때 호출되는 함수 -> 아이템뷰 객체를 만들어서 뷰홀더에 던져준다.
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LockerSaveAlbumRecyclerAdpater.viewHolder {
        val binding : ItemLockerSaveAlbumBinding = ItemLockerSaveAlbumBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return viewHolder(binding)
    }

    // 뷰홀더에 데이터를 바인딩해줘야 할 때마다 호출되는 함수 -> 엄청나게 많이 호출될 것이다.
    override fun onBindViewHolder(holder: LockerSaveAlbumRecyclerAdpater.viewHolder, position: Int) {
        holder.bind(albums[position])
    }

    //데이터 set의 크기를 알려주는 함수 -> 리사이클러뷰가 마지막이 언제인지를 알게 된다.
    override fun getItemCount(): Int = albums.size

    //뷰홀더
    inner class viewHolder(val binding: ItemLockerSaveAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (album : Album){
            binding.itemLockerSaveAlbumTitleTv.text = album.title
            binding.itemLockerSaveAlbumSingerTv.text = album.singer
            binding.itemLockerSaveAlbumImg.setImageResource(album.coverImg!!)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAlbums(albums: ArrayList<Album>) {
        this.albums.clear()
        this.albums.addAll(albums)
        notifyDataSetChanged()
    }

    fun removeSong(position: Int){
        albums.removeAt(position)
        notifyDataSetChanged()
    }
}