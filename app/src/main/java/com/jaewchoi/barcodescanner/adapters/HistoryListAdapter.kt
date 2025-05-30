package com.jaewchoi.barcodescanner.adapters

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaewchoi.barcodescanner.databinding.ItemHistoryListBinding
import com.jaewchoi.barcodescanner.data.source.local.ScanHistory

class HistoryListAdapter(
    val onDeleteHistory: (id: Long) -> Unit,
    val onRecordFromSheet: (barcodeValue: String) -> Unit,
    val onURLAddress: (url: String?) -> Unit
) :
    ListAdapter<ScanHistory, HistoryListAdapter.HistoryViewHolder>(DiffCallback) {

    private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder(
        ItemHistoryListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ScanHistory>() {
        override fun areItemsTheSame(
            oldItem: ScanHistory,
            newItem: ScanHistory
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ScanHistory,
            newItem: ScanHistory
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScanHistory) {
            binding.history = item

            val isExpanded = adapterPosition == expandedPosition

            if (isExpanded) {
                expand(binding.drawer)
                binding.chevronIcon.animate()
                    .rotation(90f)
                    .setDuration(300)
                    .start()
            } else {
                collapse(binding.drawer)
                binding.chevronIcon.animate()
                    .rotation(0f)
                    .setDuration(300)
                    .start()
            }

            binding.root.setOnClickListener {
                val old = expandedPosition
                expandedPosition = if (isExpanded) -1 else adapterPosition
                if (old != -1) notifyItemChanged(old)
                notifyItemChanged(adapterPosition)
            }

            binding.btnCopy.setOnClickListener {
                val clipboard =
                    itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("barcode", item.barcode)
                clipboard.setPrimaryClip(clip)

            }
            binding.btnDelete.setOnClickListener {
                onDeleteHistory(item.id)
            }
            binding.btnSheet.setOnClickListener {
                onRecordFromSheet(item.barcode)
            }
            binding.btnUrl.setOnClickListener {
                onURLAddress(item.url)
            }
        }
    }


    private fun expand(view: View) {
        view.measure(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val targetHeight = view.measuredHeight
        view.layoutParams.height = 0
        view.visibility = View.VISIBLE

        ValueAnimator.ofInt(0, targetHeight).apply {
            duration = 300
            addUpdateListener { anim ->
                view.layoutParams.height = anim.animatedValue as Int
                view.requestLayout()
            }
            start()
        }
    }

    private fun collapse(view: View) {
        val initialHeight = view.measuredHeight
        ValueAnimator.ofInt(initialHeight, 0).apply {
            duration = 300
            addUpdateListener { anim ->
                view.layoutParams.height = anim.animatedValue as Int
                view.requestLayout()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                }
            })
            start()
        }
    }
}
