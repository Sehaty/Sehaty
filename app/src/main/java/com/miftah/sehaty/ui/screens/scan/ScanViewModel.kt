package com.miftah.sehaty.ui.screens.scan

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.miftah.sehaty.domain.usecase.scan.SaveScanningImageToCloud
import com.miftah.sehaty.domain.usecase.scan.SendScanningImage
import com.miftah.sehaty.ui.screens.onboarding.OnBoardingState
import com.miftah.sehaty.utils.reduceFileImage
import com.miftah.sehaty.utils.reduceFileImageCompat
import com.miftah.sehaty.utils.uriToFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.lang.ref.WeakReference
import javax.inject.Inject


@HiltViewModel
class ScanViewModel @Inject constructor(
    val sendScanningImage: SendScanningImage,
    val sendToCloud: SaveScanningImageToCloud
) : ViewModel() {

    /* private val _imageBitmap = MutableStateFlow<Bitmap?>(null)
     val bitmaps = _imageBitmap.asStateFlow()

     private val _imageUri = MutableStateFlow<Uri?>(null)
     val imageUri = _imageUri.asStateFlow()

     private val _visiblePermissionDialogQueue = mutableStateListOf<String>()
     val visiblePermissionDialogQueue = _visiblePermissionDialogQueue.toList()

     private var _isPermissionAcquire = MutableStateFlow(false)
     val isPermissionAcquire = _isPermissionAcquire.asStateFlow()

     private var _itemName = MutableStateFlow("")
     val itemName = _itemName.asStateFlow()*/

    private var contextRef: WeakReference<Context>? = null

    private val _scanState = mutableStateOf(ScanState())
    val scanState: State<ScanState>
        get() = _scanState

    fun onEvent(event: ScanEvent) {
        when (event) {
            is ScanEvent.SetContextWeakReference -> {
                contextRef = WeakReference(event.context)
            }

            is ScanEvent.SaveToUri -> {
                saveToUri(event.uri)
            }

            is ScanEvent.WriteItemTitle -> {
                _scanState.value = _scanState.value.copy(
                    imageTitle = event.text
                )
            }

            is ScanEvent.ScanImage -> {
                scanningImage(_scanState.value.imageUri)
            }

            ScanEvent.ClearUri -> {
                clearUri()
            }

            ScanEvent.CleanScanImage -> {
                cleanScanImage()
            }
        }
    }

    private fun saveToUri(uri: Uri? = null) {
        _scanState.value = _scanState.value.copy(
            imageUri = uri
        )
        convertUriToBitMap()
    }

    private fun clearUri() {
        _scanState.value = _scanState.value.copy(
            imageUri = null
        )
    }

    private fun convertUriToBitMap() {
        contextRef?.get()?.let { context ->
            _scanState.value.imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    _scanState.value = _scanState.value.copy(
                        imageBitmap = MediaStore.Images
                            .Media.getBitmap(context.contentResolver, it)
                    )
                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, it)
                    _scanState.value = _scanState.value.copy(
                        imageBitmap = ImageDecoder.decodeBitmap(source)
                    )
                }
            }
        }
    }

    private fun scanningImage(uri: Uri?) {
        if ((uri != null) || (contextRef?.get() != null)) {
            val imageFile = uriToFile(uri!!, contextRef?.get()!!)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                imageFile.reduceFileImage()
//                Log.d("URI -> FILE", imageFile.path)
                _scanState.value = _scanState.value.copy(
                    scanImageResult = sendScanningImage(imageFile)
                )
            } else {
                val reducedFile = imageFile.reduceFileImageCompat()
//                Log.d("URI -> FILE (Compat)", reducedFile.path)
                _scanState.value = _scanState.value.copy(
                    scanImageResult = sendScanningImage(reducedFile)
                )
            }
        }
    }

    private fun cleanScanImage() {
        _scanState.value = _scanState.value.copy(
            scanImageResult = null
        )
    }
}