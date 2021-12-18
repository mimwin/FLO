package com.example.flo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.R
import com.example.flo.data.Song

class VideoFragmentRecyclerAdapter(private val items : ArrayList<Song>) :
RecyclerView.Adapter<VideoFragmentRecyclerAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.v3_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listener = View.OnClickListener { it ->

        }
        holder.bind(listener, items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
        fun bind(listener : View.OnClickListener, items : Song){
            view.setOnClickListener(listener)
            val title = view.findViewById<TextView>(R.id.v3_row_title)
            val singer = view.findViewById<TextView>(R.id.v3_row_singer)
            title.text = items.title
            singer.text = items.singer
        }
    }
}