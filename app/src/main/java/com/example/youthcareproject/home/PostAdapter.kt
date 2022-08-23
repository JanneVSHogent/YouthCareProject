package com.example.youthcareproject.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.youthcareproject.databinding.ListItemPostBinding
import com.example.youthcareproject.database.Post

class PostAdapter(val clickListener: PostListener): ListAdapter<Post, PostAdapter.ViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(getItem(position)!!, clickListener)
    }


    //Inner class
    class ViewHolder private constructor(val binding: ListItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post, clickListener: PostListener){

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPostBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

class PostListener(val clickListener: (postId: Long) -> Unit) {
    fun onClick(post: Post) = clickListener(post.postId)
}