package com.example.flo.locker_viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.RoomDB.SongDatabase
import com.example.flo.adapter.LockerSaveAlbumRecyclerAdpater
import com.example.flo.adapter.getUserIdx
import com.example.flo.databinding.FragmentLockerSaveAlbumBinding

class SaveAlbumFragment : Fragment() {

    lateinit var binding : FragmentLockerSaveAlbumBinding
    lateinit var albumDB: SongDatabase

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSaveAlbumBinding.inflate(inflater,container,false)

        albumDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview() {
        val LockerSaveAlbumRecyclerAdpater = LockerSaveAlbumRecyclerAdpater()

        binding.lockerSaveRecyclerview.adapter = LockerSaveAlbumRecyclerAdpater
        binding.lockerSaveRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        LockerSaveAlbumRecyclerAdpater.setMyItemClickListener(object : LockerSaveAlbumRecyclerAdpater.MyItemClickListener{
            override fun onRemoveAlbum(songId: Int) {
                albumDB.songDao().updateIsLikeById(false, songId)
            }
        })

        LockerSaveAlbumRecyclerAdpater.addAlbums(albumDB.albumDao().getLikeAlbums(getUserIdx(requireContext())) as ArrayList)

    }

}