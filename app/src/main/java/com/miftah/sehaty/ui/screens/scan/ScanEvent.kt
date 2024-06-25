package com.miftah.sehaty.ui.screens.scan

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

sealed class ScanEvent {

    data class SetContextWeakReference(val context : Context) : ScanEvent()

    data class SaveToUri(val uri : Uri? = null) : ScanEvent()

    data class WriteItemTitle(val text : String) : ScanEvent()

    data object ScanImage : ScanEvent()

    data object ClearUri : ScanEvent()
}