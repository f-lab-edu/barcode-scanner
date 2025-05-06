package com.jaewchoi.barcodescanner.adapters

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.jaewchoi.barcodescanner.data.source.local.ScanHistory
import com.jaewchoi.barcodescanner.data.source.network.Record
import com.jaewchoi.barcodescanner.ui.model.RecordListItem

@BindingAdapter("record")
fun bindRecordList(
    view: RecyclerView,
    record: Record?
) {
    if (record == null) return
    val adapter = view.adapter as RecordListAdapters
    val list = record.values.map {
        RecordListItem(it.key, it.value)
    }
    adapter.submitList(list)
}

@BindingAdapter("histories")
fun bindHistoryList(
    view: RecyclerView,
    histories: List<ScanHistory>?
) {
    if (histories == null) return
    val adapter = view.adapter as HistoryListAdapter
    adapter.submitList(histories)
}


@BindingAdapter("app:bindText")
fun bindText(
    view: EditText,
    value: String?,
) {
    if (view.text.toString() != (value ?: "")) {
        view.setText(value ?: "")
        view.setSelection(view.text.length)
    }
}

@InverseBindingAdapter(attribute = "app:bindText", event = "app:bindTextAttrChanged")
fun captureText(view: EditText): String {
    return view.text.toString()
}

@BindingAdapter("app:bindTextAttrChanged")
fun setTextWatcher(
    view: EditText,
    listener: InverseBindingListener?
) {
    if (listener == null) return

    view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            listener.onChange()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}
