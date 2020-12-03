package com.example.afop.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.afop.data.model.MeetingSummaryDTO
import com.example.afop.databinding.ItemMeetingSummaryBinding
import com.example.afop.ui.activity.MarketActivity
import com.example.afop.ui.activity.MeetingActivity
import com.example.afop.util.ActivityExtendFunction
import com.example.afop.util.Util

class MeetingListAdapter(val context: Context?) :
    ListAdapter<MeetingSummaryDTO, MeetingListAdapter.ViewHolder>(MeetingListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMeetingSummaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItem(position: Int): MeetingSummaryDTO {
        return super.getItem(position)
    }

    inner class ViewHolder(val binding: ItemMeetingSummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(_item: MeetingSummaryDTO) {
            binding.apply {
                item = _item
                executePendingBindings()
            }
            binding.root.setOnClickListener {
                context?.apply {
                    Intent(this, MeetingActivity::class.java).putExtra(ActivityExtendFunction.ActivityType.READ.name, _item.id)
                }
            }
        }
    }
}

private class MeetingListDiffCallback : DiffUtil.ItemCallback<MeetingSummaryDTO>() {
    override fun areItemsTheSame(oldItem: MeetingSummaryDTO, newItem: MeetingSummaryDTO): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MeetingSummaryDTO, newItem: MeetingSummaryDTO): Boolean {
        return oldItem == newItem
    }
}