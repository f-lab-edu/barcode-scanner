package com.jaewchoi.barcodescanner.utils

import androidx.annotation.StringRes

sealed class UiEvent {
    object DismissDialog : UiEvent()

    data class ShowToast(@StringRes val resId: Int) : UiEvent()

    data class CopyToClipboard(val text: String) : UiEvent()

    data class OpenUrl(val url: String) : UiEvent()
}