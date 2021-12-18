package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.RoomDB.SongDatabase
import com.example.flo.`interface`.SongService
import com.example.flo.adapter.*
import com.example.flo.data.Album
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.home_viewpager.HomeImageFragment
import com.example.flo.view.HomeLatestView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson


class HomeFragment : Fragment(),HomeLatestView {
    lateinit var binding: FragmentHomeBinding

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var banner : Banner
    private lateinit var viewpager : ViewPager2

    private var albums = ArrayList<Album>()
    private lateinit var songDB: SongDatabase

    private lateinit var HomeLatestAdapter : HomeLatestRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //ROOM_DB
        songDB = SongDatabase.getInstance(requireContext())!!
        albums.addAll(songDB.albumDao().getAlbums()) // songDB에서 album list를 가져옵니다.

        inithomeAlbumRecyclerview()
        initLatesAlbumRecyclerview()
        initBanner()
        initViewPager()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getAlbums()
    }

    private fun getAlbums() {
        val songService = SongService()
        songService.setHomeAlbumView(this)

        songService.getAlbums()
    }


    private fun inithomeAlbumRecyclerview(){
        val HomeAlbumRecyclerAdapter = HomeAlbumRecyclerAdapter(albums)

        //레이아웃 매니저 설정
        binding.homeTodayMusicAlbumRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        HomeAlbumRecyclerAdapter.setMyItemClickListener(object : HomeAlbumRecyclerAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }
            override fun onPlaybtnClick(album: Album) {
                (context as MainActivity).binding.mainMiniplayerTitleTv.text = album.title
                (context as MainActivity).binding.mainMiniplayerSingerTv.text = album.singer
            }
        })
        //리사이클러뷰에 어뎁터를 연결
        binding.homeTodayMusicAlbumRecyclerview.adapter = HomeAlbumRecyclerAdapter

    }

    fun initLatesAlbumRecyclerview(){
        HomeLatestAdapter = HomeLatestRecyclerAdapter(requireContext())

        HomeLatestAdapter.setMyItemClickListener(object  : HomeLatestRecyclerAdapter.MyItemClickListener{
            override fun onItemClick(album: Album, position : Int) {
                saveAlbumIdx(requireContext(),position+1)
                Log.e("SAVEALBUMINDEX", album.toString())
                Log.e("SAVEALBUMINDEX", getAlbumIndex(requireContext()).toString())
                (context as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, AlbumFragment().apply {
                            arguments = Bundle().apply {
                                val gson = Gson()
                                val albumJson = gson.toJson(album)
                                putString("albumJson", albumJson)
                            }
                        })
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
            }
        })

        binding.homeLatestalbumRecyclerview.adapter = HomeLatestAdapter
    }

    fun initBanner(){
        val bannerAdapter = BannerViewPagerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))

        binding.homeBannerImg.adapter = bannerAdapter
        binding.homeBannerImg.orientation = ViewPager2.ORIENTATION_HORIZONTAL

//        banner = Banner(0)
//        banner.start()
    }

    inner class Banner(private var currentPos : Int) : Thread(){
        override fun run() {
            while(true){
                sleep(2000)
                handler.post {
                    if(currentPos==6) currentPos=0
                    viewpager.setCurrentItem(currentPos,true)
                    currentPos+=1
                }
            }
        }
    }

    fun initViewPager(){
        viewpager = binding.homeViewPager

        val imageAdapter = HomeImageViewPagerAdapter(this)
        imageAdapter.addFragment(HomeImageFragment(R.drawable.img_default_4_x_1))
        imageAdapter.addFragment(HomeImageFragment(R.drawable.cd))
        imageAdapter.addFragment(HomeImageFragment(R.drawable.img_default_4_x_1))
        imageAdapter.addFragment(HomeImageFragment(R.drawable.cd))
        imageAdapter.addFragment(HomeImageFragment(R.drawable.img_default_4_x_1))
        imageAdapter.addFragment(HomeImageFragment(R.drawable.cd))


        viewpager.adapter = imageAdapter
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val tablayout = binding.homeTablayout
        TabLayoutMediator(tablayout, viewpager) { tab, position ->
        }.attach()
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment().apply {
                    arguments = Bundle().apply {
                        val gson = Gson()
                        val albumJson = gson.toJson(album)
                        putString("albumJson", albumJson)
                    }
                })
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    override fun onDestroy() {
        //banner.interrupt()
        super.onDestroy()
    }

    override fun onGetAlbumsLoading() {
        binding.homeLoadingPb.visibility = View.VISIBLE
    }

    override fun onGetAlbumsSuccess(albums: ArrayList<Album>) {
        binding.homeLoadingPb.visibility = View.GONE

        HomeLatestAdapter.addAlbums(albums)
    }

    override fun onGetAlbumsFailure(code: Int, message: String) {
        binding.homeLoadingPb.visibility = View.GONE

        when(code){
            400 -> Log.d("LOOKFRAG/API",message)
        }
    }
}