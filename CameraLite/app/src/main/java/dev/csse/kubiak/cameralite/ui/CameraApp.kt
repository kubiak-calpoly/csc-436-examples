package dev.csse.kubiak.cameralite.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Save

import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraApp(
  model: CameraViewModel = viewModel()
) {
  val imageExists = when (model.captureMode) {
    CaptureMode.FILE -> !model.isImageEmpty
    CaptureMode.BUFFER -> !model.isBitmapEmpty
  }
  val context: Context = LocalContext.current
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("CameraLite") },
        actions = {
          IconButton(
            onClick = {
              model.bitmap = model.compositeImage()
            },
            enabled = model.captureMode == CaptureMode.BUFFER && model.bitmap != null
          ) {
            Icon(
              Icons.Filled.GridView,
              contentDescription = "Multiply 2x2 Grid"
            )
          }
          IconButton(
            onClick = {
              model.imageUri = model.saveImage(context)
            },
            enabled = model.captureMode == CaptureMode.BUFFER && model.bitmap != null
          ) {
            Icon(
              Icons.Filled.Save,
              contentDescription = "Save to File"
            )
          }
          IconButton(
            onClick = {
              when (model.captureMode) {
                CaptureMode.FILE -> model.imageUri = null
                CaptureMode.BUFFER -> model.bitmap = null
              }
            },
            enabled = imageExists
          ) {
            Icon(
              Icons.Filled.DeleteOutline,
              contentDescription = "Delete Image"
            )
          }
        }
      )
    },
    bottomBar = {
      SingleChoiceSegmentedButtonRow {
        SegmentedButton(
          label = { Text("File") },
          selected = model.captureMode == CaptureMode.FILE,
          onClick = { model.captureMode = CaptureMode.FILE },
          shape = SegmentedButtonDefaults.itemShape(0, 2)
        )
        SegmentedButton(
          label = { Text("In-Memory") },
          selected = model.captureMode == CaptureMode.BUFFER,
          onClick = { model.captureMode = CaptureMode.BUFFER },
          shape = SegmentedButtonDefaults.itemShape(1, 2)
        )
      }
    }, floatingActionButton = {

    }
  ) { innerPadding ->

    if (imageExists) {
      Box(modifier = Modifier.fillMaxSize()) {
        val description = when (model.captureMode) {
          CaptureMode.FILE -> "Image from File"
          CaptureMode.BUFFER -> "Image in Memory"
        }
        when (model.captureMode) {
          CaptureMode.FILE -> Image(
            modifier = Modifier
              .padding(innerPadding)
              .align(Alignment.Center),
            painter = rememberAsyncImagePainter(model.imageUri),
            contentDescription = description
          )

          CaptureMode.BUFFER -> Image(
            (model.bitmap ?: model.emptyBitmap).asImageBitmap(),
            modifier = Modifier
              .padding(innerPadding)
              .align(Alignment.Center),
            contentDescription = description
          )
        }

        Text(
          description,
          modifier = Modifier.align(Alignment.BottomCenter)
        )
      }
    } else {
      CameraCapture(
        modifier = Modifier
          .padding(innerPadding)
          .background(Color.Black),
        onImageFile = { file ->
          model.imageUri = file.toUri()
        },
        onImageBuffer = { image ->
          model.bitmap = image.toBitmap()
        }
      )
    }
  }
}

