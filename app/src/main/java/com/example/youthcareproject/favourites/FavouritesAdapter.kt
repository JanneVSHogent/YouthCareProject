package com.example.youthcareproject.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youthcareproject.R
import com.example.youthcareproject.TextItemViewHolder
import com.example.youthcareproject.database.Post

class FavouritesAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<Post>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int){
        val item = data[position]
        holder.textView.text = item.description.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }
}