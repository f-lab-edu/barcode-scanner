package com.jaewchoi.barcodescanner.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaewchoi.barcodescanner.data.source.network.Record
import com.jaewchoi.barcodescanner.ui.model.RecordListItem

@BindingAdapter("record")
fun bindRecordList(
    view: RecyclerView,
    record: Record?
) {
    if (record == null) return
    val adapter = view.adapter as RecordListAdapters
    val list = listOf(
        RecordListItem("id", record.id),
        RecordListItem("barcode", record.barcode),
        RecordListItem("name", record.name),
        RecordListItem("price", record.price),
    )
    adapter.submitList(list)
}