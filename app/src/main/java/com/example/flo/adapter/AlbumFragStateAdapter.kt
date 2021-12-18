package com.example.flo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.album_viewpager.DetailFragment
import com.example.flo.album_viewpager.SongFragment
import com.example.flo.album_viewpager.VideoFragment

class AlbumFragStateAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SongFragment()
            1 -> DetailFragment()
            2 -> VideoFragment()
            else -> SongFragment()
        }
    }
}