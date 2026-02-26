package dev.csse.kubiak.cameralite.ui

import android.content.Context
import android.text.style.IconMarginSpan
import android.util.Log
import androidx.camera.core.CameraExecutor
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked

import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScrollModifierNode
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import dev.csse.kubiak.cameralite.CameraApplication
import dev.csse.kubiak.cameralite.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraApp(
  model: CameraViewModel = viewModel()
) {

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("CameraLite") },
        actions = {
        }
      )
    }
  ) { innerPadding ->

    if (model.isImageEmpty) {
      CameraCapture(
        modifier = Modifier
          .padding(innerPadding)
          .background(Color.Black),
        onImageFile = { file ->
          model.imageUri = file.toUri()
        }
      )
    } else {
      Image(
        modifier = Modifier.padding(innerPadding),
        painter = rememberAsyncImagePainter(model.imageUri),
        contentDescription = "Captured image"
      )
    }
  }
}

