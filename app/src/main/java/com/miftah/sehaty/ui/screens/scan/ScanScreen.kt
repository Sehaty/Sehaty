package com.miftah.sehaty.ui.screens.scan

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RestrictTo
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.miftah.sehaty.domain.model.FoodAfterScan
import com.miftah.sehaty.ui.screens.common.ButtonPrimary
import com.miftah.sehaty.ui.theme.Grey50
import com.miftah.sehaty.ui.theme.GreyText
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.ui.theme.dimens
import com.miftah.sehaty.utils.UiState
import com.miftah.sehaty.utils.reduceFileImage
import com.miftah.sehaty.utils.reduceFileImageCompat
import com.miftah.sehaty.utils.saveBitmapToFile
import com.miftah.sehaty.utils.uriToFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miftah.sehaty.R
import com.miftah.sehaty.ui.theme.Green70
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
    onEvent: (ScanEvent) -> Unit,
    state: ScanState,
    navigateToDetail: (FoodAfterScan) -> Unit,
    backToHistory: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    var lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) }

    onEvent(ScanEvent.SetContextWeakReference(context))
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent(),
        onResult = {
            onEvent(ScanEvent.SaveToUri(it))
            scope.launch {
                scaffoldState.bottomSheetState.expand()
            }
        }
    )

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )

        }
    }

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    LaunchedEffect(lensFacing) {
        controller.setCameraSelector(cameraSelector)
    }

    val imageCropLauncher =
        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
            if (result.isSuccessful) {
                onEvent(ScanEvent.SaveToUri(result.uriContent))
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            } else {
                println("ImageCropping error: ${result.error}")
            }
        }

    val cropOptions =
        CropImageContractOptions(state.imageUri, CropImageOptions(imageSourceIncludeCamera = true))



    if (state.imageUri != null) {
        BottomSheetToShowResult(
            state = state,
            onEvent = onEvent,
            scaffoldState = scaffoldState,
            navigateToDetail = navigateToDetail,
            onCropAction = {
                imageCropLauncher.launch(cropOptions)
            },
            backToHistory = backToHistory
        )
    } else {
        ExecuteCameraXOrOpenGallery(
            onEvent = onEvent,
            context = context,
            controller = controller,
            scaffoldState = scaffoldState,
            scope = scope,
            launcher = launcher,
            lensFacing = {
                lensFacing =
                    if (lensFacing == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
            },
            backToHistory = backToHistory
        )
    }
}

object CameraPermissions {
    val CAMERAX_PERMISSION = arrayOf(Manifest.permission.CAMERA)
}

fun hasRequiredPermissions(context: Context): Boolean {
    return CameraPermissions.CAMERAX_PERMISSION.all {
        ContextCompat.checkSelfPermission(
            context,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetToShowResult(
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState,
    onEvent: (ScanEvent) -> Unit,
    onCropAction: (() -> Unit),
    state: ScanState,
    navigateToDetail: (FoodAfterScan) -> Unit,
    backToHistory: () -> Unit
) {
    val texts = listOf(
        "Scanning Image",
        "Menghitung Nutrisi",
        "Hampir Selesai, Mohon Tunggu"
    )

    var currentIndex by remember { mutableStateOf(0) }
    val displayedTexts = remember { mutableStateListOf<String>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.scanImageResult) {
        if (state.scanImageResult != null) {
            while (currentIndex < texts.size) {
                delay(1500)
                displayedTexts.add(texts[currentIndex])
                currentIndex++
            }
        }
    }

    BottomSheetScaffold(
        modifier = modifier.windowInsetsPadding(WindowInsets.ime),
        sheetSwipeEnabled = true,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Box(
                modifier = Modifier.wrapContentSize()
            ) {
                if (state.scanImageResult != null) {
                    Column(
                        modifier = modifier.padding(16.dp)
                    ) {
                       Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                       ){
                           Text("Menghitung Nutrisi",
                               style = MaterialTheme.typography.labelLarge,
                               textAlign = TextAlign.Center)
                       }
                        Spacer(
                            modifier = modifier.height(16.dp)


                        )
                        Column(
//                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ){
                            displayedTexts.forEachIndexed { index, text ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    val infiniteTransition = rememberInfiniteTransition()
                                    val color by infiniteTransition.animateColor(
                                        initialValue = Green70,
                                        targetValue = if (index == displayedTexts.size - 1)  Color.Transparent else Green70 ,
                                        animationSpec = infiniteRepeatable(
                                            animation = tween(durationMillis = 1500),
                                            repeatMode = RepeatMode.Reverse
                                        ), label = "Effect jedag jedug"
                                    )


                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clip(CircleShape)
                                                .background(color)
                                        )
                                        if (index < displayedTexts.size - 1) {
                                            val lineColor = if (index < currentIndex - 1) Green70 else Grey50

                                            Canvas(
                                                modifier = Modifier
                                                    .width(2.dp)
                                                    .height(32.dp)
                                            ) {
                                                drawLine(
                                                    color = lineColor,
                                                    start = Offset(center.x, 0f),
                                                    end = Offset(center.x, size.height),
                                                    strokeWidth = 2.dp.toPx()
                                                )
                                            }
                                        }

                                    }


                                    Text(
                                        text = text,
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            color = Grey50,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = modifier.height(32.dp))

                    }

            }else{
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Masukan Nama Makanan",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(MaterialTheme.dimens.small1)
                        )
                        Text(
                            text = "Agar produk lebih mudah dikenali dalam riwayat pemindaian Anda",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = GreyText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Spacer(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(MaterialTheme.dimens.small2)
                        )
                        OutlinedTextField(
                            modifier = modifier.fillMaxWidth(),
                            value = state.imageTitle,
                            onValueChange = {
                                onEvent(ScanEvent.WriteItemTitle(it))
                            },
                            maxLines = 1,
                            singleLine = true,
                        )
                        Spacer(modifier = modifier.height(MaterialTheme.dimens.small2))
                        Row(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            ButtonPrimary(
                                modifier = modifier
                                    .height(MaterialTheme.dimens.buttonHeight)
                                    .weight(1f),
                                title = "NEXT",
                                textColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.primary
                            ) {
                                onEvent(ScanEvent.ScanImage)
                            }
                        }
                }

                }

//                state.scanImageResult?.collectAsState(initial = null)?.value.let { result ->
//                    when (result) {
//                        is UiState.Error -> {
//                            AlertDialog(
//                                dismissButton = {
//                                    TextButton(
//                                        onClick = { },
//                                        modifier = Modifier.padding(8.dp),
//                                    ) {
//                                        Text("Dismiss")
//                                    }
//                                },
//                                onDismissRequest = {
//
//                                },
//                                confirmButton = {
//                                    TextButton(
//                                        onClick = { },
//                                        modifier = Modifier.padding(8.dp),
//                                    ) {
//                                        Text("Confirm")
//                                    }
//                                },
//                                title = { Text(text = "Err") },
//                                text = { Text(text = result.error) }
//                            )
//                        }
//
//                        UiState.Loading -> {
//                            LinearProgressIndicator(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .align(Alignment.TopCenter),
//                                color = MaterialTheme.colorScheme.onSecondaryContainer,
//                                trackColor = MaterialTheme.colorScheme.secondaryContainer,
//                            )
//                        }
//
//                        is UiState.Success -> {
//                            navigateToDetail(
//                                result.data.copy(
//                                    productName = state.imageTitle
//                                )
//                            )
//                        }
//
//                        null -> {}
//                    }
//                }
            }
        }) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            state.imageBitmap?.let {

                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    contentScale = ContentScale.Crop
                )
            }
                state.scanImageResult?.collectAsState(initial = null)?.value.let { result ->
                    when (result) {
                        is UiState.Error -> {
                            AlertDialog(
                                dismissButton = {
                                    TextButton(
                                        onClick = { },
                                        modifier = Modifier.padding(8.dp),
                                    ) {
                                        Text("Dismiss")
                                    }
                                },
                                onDismissRequest = {},
                                confirmButton = {
                                    TextButton(
                                        onClick = { },
                                        modifier = Modifier.padding(8.dp),
                                    ) {
                                        Text("Confirm")
                                    }
                                },
                                title = { Text(text = "Err") },
                                text = { Text(text = result.error) }
                            )
                        }

                        UiState.Loading -> {
                            state.imageBitmap?.let {

                                val scope = rememberCoroutineScope()
                                val offset = remember {
                                    androidx.compose.animation.core.Animatable(
                                        0f
                                    )
                                }
                                var screenHeight by remember { mutableStateOf(0f) }

                                LaunchedEffect(Unit) {
                                    scope.launch {
                                        while (true) {
                                            offset.animateTo(
                                                targetValue = screenHeight,
                                                animationSpec = tween(
                                                    durationMillis = 2000,
                                                    easing = LinearEasing
                                                )
                                            )
                                            offset.snapTo(0f)
                                        }
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.5f))
                                        .onGloballyPositioned { layoutCoordinates ->
                                            screenHeight = layoutCoordinates.size.height.toFloat()
                                        }
                                ) {
                                    Canvas(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .align(Alignment.TopCenter)
                                    ) {
                                        drawLine(
                                            color = Color.Green,
                                            start = Offset(0f, offset.value),
                                            end = Offset(size.width, offset.value),
                                            strokeWidth = 5f
                                        )
                                    }
                                }
                            }
                        }

                        is UiState.Success -> {
                            navigateToDetail(
                                result.data.copy(
                                    productName = state.imageTitle
                                )
                            )
                        }

                        null -> {}
                    }
                }

                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black.copy(alpha = 0.5f)),
                    onClick = onCropAction,
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Crop,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        Text(
                            text = "Crop Image",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp

                            )
                        )
                    }
                }
                IconButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .size(32.dp),
                    onClick = backToHistory
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ExecuteCameraXOrOpenGallery(
        modifier: Modifier = Modifier,
        controller: LifecycleCameraController,
        launcher: ManagedActivityResultLauncher<String, Uri?>,
        context: Context,
        scope: CoroutineScope,
        scaffoldState: BottomSheetScaffoldState,
        lensFacing: () -> Unit,
        onEvent: (ScanEvent) -> Unit,
        backToHistory: () -> Unit
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CameraX(controller = controller, modifier = modifier.fillMaxSize())
                Column(
                    modifier = modifier.align(Alignment.BottomCenter)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(
                            modifier = Modifier.size(64.dp),
                            onClick = {
                                launcher.launch("image/*")
                            },
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Default.Photo,
                                contentDescription = "Open Gallery",
                            )
                        }
                        Button(
                            modifier = Modifier.size(72.dp),
                            onClick = {
                                takePhoto(
                                    controller = controller,
                                    context = context,
                                    onPhotoTaken = { bitmap ->
                                        onEvent(
                                            ScanEvent.SaveToUri(
                                                saveBitmapToFile(
                                                    context,
                                                    bitmap
                                                )
                                            )
                                        )
                                        scope.launch {
                                            scaffoldState.bottomSheetState.expand()

                                        }
                                    }
                                )
                            },
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Take Photo "
                            )
                        }
                        Button(
                            modifier = modifier.size(64.dp),
                            onClick = lensFacing,
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cameraswitch,
                                contentDescription = "Switch Lens"
                            )
                        }
                    }
                    Spacer(
                        modifier = modifier
                            .height(MaterialTheme.dimens.medium2)
                            .fillMaxWidth()
                    )
                }
                IconButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .size(32.dp),
                    onClick = backToHistory
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }

    @Composable
    fun ShowLoadingIndicator(state: ScanState) {
        Column {
            Text("Loading")
        }
    }

    private fun takePhoto(
        controller: LifecycleCameraController,
        context: Context,
        onPhotoTaken: (Bitmap) -> Unit
    ) {
        if (!hasRequiredPermissions(context)) {
            return
        }
        controller.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                        postScale(1f, 1f)
                    }
                    val rotatedBitMap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )
                    onPhotoTaken(rotatedBitMap)
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.d("Camera ", "Could't take photo", exception)
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true)
    @Composable
    private fun PreviewScanScreen() {
        val scaffoldState = rememberBottomSheetScaffoldState()
        LaunchedEffect(true) {
            scaffoldState.bottomSheetState.expand()
        }
        SehatyTheme {
            BottomSheetToShowResult(
                onCropAction = {},
                navigateToDetail = {},
                scaffoldState = scaffoldState,
                state = ScanState(),
                onEvent = {},
                backToHistory = {}
            )
        }
    }


