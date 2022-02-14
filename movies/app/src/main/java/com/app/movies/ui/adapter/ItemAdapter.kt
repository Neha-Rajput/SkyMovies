package com.app.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.app.movies.data.Data
import com.app.movies.databinding.ItemViewBinding

class ItemAdapter : ListAdapter<Data, ItemViewHolder>(ItemAdapter) {
    companion object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemViewBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding as ItemViewBinding
        val movieData = getItem(position)
        binding.run {
            data = movieData
            /* Glide.with(imageView).load(movieData.poster)
                 .placeholder(R.drawable.ic_launcher_background)
                 .error(R.drawable.ic_launcher_background).into(imageView)*/
            executePendingBindings()
        }
    }
}

class ItemViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)