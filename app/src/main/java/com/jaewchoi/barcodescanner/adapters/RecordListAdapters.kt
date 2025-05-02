package com.jaewchoi.barcodescanner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaewchoi.barcodescanner.databinding.ItemRecordDialogBinding
import com.jaewchoi.barcodescanner.ui.model.RecordListItem

class RecordListAdapters :
    ListAdapter<RecordListItem, RecordListAdapters.FiledViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FiledViewHolder(
        ItemRecordDialogBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: FiledViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RecordListItem>() {
        override fun areItemsTheSame(
            oldItem: RecordListItem,
            newItem: RecordListItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: RecordListItem,
            newItem: RecordListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class FiledViewHolder(private val binding: ItemRecordDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecordListItem) {
            binding.recordListItem = item
        }
    }
}