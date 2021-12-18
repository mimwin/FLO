package com.example.flo.adapter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun saveJwt(context : Context, jwt : String){
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("jwt",jwt)
    editor.apply()
}

fun getJwt(context : Context) : String{
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("jwt","")!!
}

fun saveUserIdx(context : Context, userIdx : Int){
    val spf = context.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("userIdx", userIdx)
    editor.apply()
}

fun getUserIdx(context : Context) : Int {
    val spf = context.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("userIdx",0)
}

fun saveuserid(context : Context, userid : Int){
    val spf = context.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("userid", userid)
    editor.apply()
}

fun getuserid(context : Context) : Int {
    val spf = context.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("userid",0)
}

fun saveAlbumIdx(context : Context, albumIdx : Int){
    val spf = context.getSharedPreferences("song", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("albumIdx",albumIdx)
    editor.apply()
}

fun getAlbumIndex(context : Context) : Int{
    val spf = context.getSharedPreferences("song", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("albumIdx",1)!!
}
