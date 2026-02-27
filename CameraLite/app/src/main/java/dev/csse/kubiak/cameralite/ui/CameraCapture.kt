package dev.csse.kubiak.cameralite.ui

import android.R
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraCapture(
  modifier: Modifier = Modifier,
  onImageFile: (File) -> Unit = { },
  onImageBuffer: (ImageProxy) -> Unit = {},
  model: CameraViewModel = viewModel()
) {
  val context = LocalContext.current
  val lifecycleOwner = LocalLifecycleOwner.current
  val coroutineScope = rememberCoroutineScope()
  var previewUseCase by remember {
    mutableStateOf<UseCase>(
      Preview.Builder().build()
    )
  }
  val imageCaptureUseCase by remember {
    mutableStateOf(
      ImageCapture.Builder()
        .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
        .build()
    )
  }

  Box(modifier = modifier) {

    CameraPreview(
      modifier = Modifier.padding(40.dp),
      onUseCase = {
        previewUseCase = it
      }
    )

    IconButton(
      modifier = Modifier
        .padding(bottom =  40.dp)
        .background(Color(0x80000000))
        .padding(16.dp)
        .align(Alignment.BottomCenter),
      onClick = {
        coroutineScope.launch {
          if (model.captureMode == CaptureMode.FILE)
            imageCaptureUseCase.takePicture(context.executor).let {
              onImageFile(it)
            }
          else
            imageCaptureUseCase.captureImage(context.executor).let {
              onImageBuffer(it)
            }
        }
      }
    ) {
      Icon(
        Icons.Filled.Camera,
        contentDescription = "Capture Image",
        modifier = Modifier.size(50.dp),
        tint = Color.White
        )
    }

  }

  LaunchedEffect(previewUseCase) {
    val cameraProvider = context.getCameraProvider()
    try {
      // Must unbind the use-cases before rebinding them.
      cameraProvider.unbindAll()
      cameraProvider.bindToLifecycle(
        lifecycleOwner, model.cameraSelector, previewUseCase, imageCaptureUseCase
      )
    } catch (ex: Exception) {
      Log.e("CameraCapture", "Failed to bind camera use cases", ex)
    }
  }
}

suspend fun ImageCapture.takePicture(executor: Executor): File {
  val photoFile = withContext(Dispatchers.IO) {
    kotlin.runCatching {
      File.createTempFile("image", "jpg")
    }.getOrElse { ex ->
      Log.e("TakePicture", "Failed to create temporary file", ex)
      File("/dev/null")
    }
  }

  return suspendCoroutine { continuation ->
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
    takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
      override fun onImageSaved(output: ImageCapture.OutputFileResults) {
        continuation.resume(photoFile)
      }

      override fun onError(ex: ImageCaptureException) {
        Log.e("CameraCapture", "Image capture failed", ex)
        continuation.resumeWithException(ex)
      }
    })
  }
}

suspend fun ImageCapture.captureImage(executor: Executor): ImageProxy {
  return suspendCoroutine { continuation ->
    takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {
      override fun onCaptureSuccess(image: ImageProxy) {
        super.onCaptureSuccess(image)
        continuation.resume(image)
      }

      override fun onError(ex: ImageCaptureException) {
        Log.e("CameraCapture", "Image capture failed", ex)
        continuation.resumeWithException(ex)
      }
    })
  }
}