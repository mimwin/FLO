package com.example.flo.locker_viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.RoomDB.SongDatabase
import com.example.flo.adapter.LockerSaveRecyclerAdapter
import com.example.flo.databinding.FragmentLockerSaveBinding

class SaveFragment : Fragment() {

    lateinit var binding : FragmentLockerSaveBinding
    lateinit var songDB: SongDatabase

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSaveBinding.inflate(inflater,container,false)

        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview() {
        val LockerSaveRecyclerAdpater = LockerSaveRecyclerAdapter()

        binding.lockerSaveRecyclerview.adapter = LockerSaveRecyclerAdpater
        binding.lockerSaveRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        LockerSaveRecyclerAdpater.setMyItemClickListener(object : LockerSaveRecyclerAdapter.MyItemClickListener{
            override fun onRemoveAlbum(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }

        })

        LockerSaveRecyclerAdpater.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList)

    }
}