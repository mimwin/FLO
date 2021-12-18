package com.example.flo.home_viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomeimageBinding

class HomeImageFragment(val imgRes : Int) : Fragment() {

    lateinit var binding : FragmentHomeimageBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeimageBinding.inflate(inflater, container, false)
        binding.homeMainImgIv.setImageResource(imgRes)
        return binding.root
    }

}