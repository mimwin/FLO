package com.example.flo.album_viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentSongDetailBinding

class DetailFragment : Fragment() {
    lateinit var binding : FragmentSongDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongDetailBinding.inflate(inflater,container,false)
        return binding.root
    }
}