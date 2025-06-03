package com.jaewchoi.barcodescanner.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import com.jaewchoi.barcodescanner.R

fun Context.openUrlInBrowser(urlString: String) {
    val uri = urlString.toUri()

    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        Toast.makeText(this, getString(R.string.fail_open_url), Toast.LENGTH_SHORT).show()
    }
}