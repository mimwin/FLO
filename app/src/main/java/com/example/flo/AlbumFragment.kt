package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.flo.RoomDB.SongDatabase
import com.example.flo.adapter.AlbumFragStateAdapter
import com.example.flo.adapter.getUserIdx
import com.example.flo.data.Album
import com.example.flo.data.Like
import com.example.flo.databinding.FragmentSongAlbumBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {

    lateinit var binding : FragmentSongAlbumBinding
    lateinit var viewPager : ViewPager2
    lateinit var tablayout : TabLayout
    val textarr = arrayListOf<String>("수록곡","상세정보","영상")
    private var gson: Gson = Gson()

    private var isLiked : Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongAlbumBinding.inflate(inflater,container, false)

        // Home 에서 넘어온 데이터 받아오기
        val albumData = arguments?.getString("albumJson")
        val album = gson.fromJson(albumData, Album::class.java)
        isLiked = isLikedAlbum(album.id)

        //Home에서 넘어온 데이터를 뷰들에 반영
        setinit(album)

        setClickListener(album)

        //init viewPager  and Tablayout
        init()

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }
        return binding.root
    }

    private fun setinit(album: Album) {
        if(album.coverImgUrl==""){
            Glide.with(requireContext()).load(album.coverImg!!).into(binding.albumAlbumImage)
        }else{
            Glide.with(requireContext()).load(album.coverImgUrl).into(binding.albumAlbumImage)
        }
        binding.albumTitleTv.text = album.title.toString()
        binding.albumSingerTv.text = album.singer.toString()

        if(isLiked){
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun setClickListener(album : Album){
        val userId : Int = getUserIdx(requireContext())

        binding.songLikeOffIv.setOnClickListener {
            if(isLiked){
                binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
                disLikeAlbum(userId,album.id)
            }else{
                binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
        }
    }

    private fun likeAlbum(userId : Int, albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }

    //이미 좋아요를 눌렀는지 확인하기 위한 함수
    //null이면 좋아요 X 이기 때문에 null이 아닌 경우만 리턴
    private fun isLikedAlbum(albumId : Int) : Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getUserIdx(requireContext())

        val likeId : Int? = songDB.albumDao().isLikeAlbum(userId,albumId)

        return likeId != null
    }

    private fun disLikeAlbum(userId: Int, albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        songDB.albumDao().disLikeAlbum(userId,albumId)
    }

    private fun init() {
        //ViewPagerAdapter
        viewPager = binding.albumViewPager
        viewPager.adapter = AlbumFragStateAdapter(this)
        //tablayout
        tablayout = binding.albumTablayout
        TabLayoutMediator(tablayout,viewPager) { tab, position ->
            tab.text = textarr[position]
        }.attach()
    }
}