package com.example.flo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.locker_viewpager.MusicFileFragment
import com.example.flo.locker_viewpager.SaveAlbumFragment
import com.example.flo.locker_viewpager.SaveFragment

class LockerFragStateAdapter (fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> SaveFragment()
            1-> MusicFileFragment()
            2-> SaveAlbumFragment()
            else->SaveFragment()
        }
    }
}