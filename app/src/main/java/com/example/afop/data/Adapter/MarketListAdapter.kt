package com.example.afop.data.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.afop.ui.activity.MarketActivity
import com.example.afop.data.model.MarketDTO
import com.example.afop.databinding.ItemMarketSummaryBinding

class MarketListAdapter(val context: Context?) : ListAdapter<MarketDTO, MarketListAdapter.ViewHolder>(
    MarketListDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMarketSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            context?.apply {
                startActivity(Intent(this, MarketActivity::class.java).putExtra("item", item))
            }
        }
    }

    class ViewHolder(val binding: ItemMarketSummaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(_item: MarketDTO) {
            binding.apply {
                item = _item
                executePendingBindings()
            }
        }
    }
}

private class MarketListDiffCallback : DiffUtil.ItemCallback<MarketDTO>() {
    override fun areItemsTheSame(oldItem: MarketDTO, newItem: MarketDTO): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItem: MarketDTO, newItem: MarketDTO): Boolean {
        return true
    }
}