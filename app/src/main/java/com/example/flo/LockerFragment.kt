package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.RoomDB.SongDatabase
import com.example.flo.adapter.LockerFragStateAdapter
import com.example.flo.adapter.getUserIdx
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    lateinit var viewPager : ViewPager2
    lateinit var tablayout : TabLayout
    val textarr = arrayListOf<String>("저장한 곡", "음악파일","저장앨범")
    lateinit var userDB: SongDatabase


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        userDB = SongDatabase.getInstance(requireContext())!!

        init()

        binding.lockerLoginTv.setOnClickListener {
            startActivity(Intent(activity,LoginActivity::class.java))
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun init() {
        viewPager = binding.lockerViewPager
        viewPager.adapter = LockerFragStateAdapter(this)

        tablayout = binding.lockerTablayout
        TabLayoutMediator(tablayout,viewPager){ tab, position ->
            tab.text = textarr[position]

        }.attach()
    }

    private fun initView(){

        //jwt 가져옴
        val jwt = getUserIdx(requireContext())

        Log.e("JWT",jwt.toString())

        if(jwt == 0){
            binding.lockerLoginTv.text = "로그인"

            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity,LoginActivity::class.java))
            }
        }
        else{

            //user의 id를 jwt 로 바꿔줘야 함.
//            val users = userDB.userDao().getUserwithId(jwt)!!

            binding.lockerLoginTv.text = "로그아웃"
//            binding.lockerUsernameTv.text = users.name

            binding.lockerLoginTv.setOnClickListener {
                //로그아웃 시켜줌.
                logout()
                activity?.finish()
                startActivity(Intent(activity,LoginActivity::class.java))
            }
        }
    }

    private fun logout(){
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt")
        editor.apply()
    }
}