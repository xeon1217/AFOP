package com.example.afop.data.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.afop.databinding.ItemViewPagerImageBinding

class ViewPagerAdapter(val context: Context?) : ListAdapter<String, ViewPagerAdapter.ViewHolder>(ViewPagerDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewPagerImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItem(position: Int): String {
        return super.getItem(position)
    }

    inner class ViewHolder(val binding: ItemViewPagerImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(_item: String) {
            binding.apply {
                item = _item
                executePendingBindings()
            }
            binding.root.setOnClickListener {

            }
        }
    }
}

private class ViewPagerDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}