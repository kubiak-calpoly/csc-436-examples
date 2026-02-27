package dev.csse.kubiak.cameralite.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap
import androidx.lifecycle.ViewModel
import kotlin.math.sqrt

enum class CaptureMode() {
  FILE,
  BUFFER
}

class CameraViewModel() : ViewModel() {

  var cameraSelector: CameraSelector by mutableStateOf(CameraSelector.DEFAULT_FRONT_CAMERA)
  var captureMode by mutableStateOf(CaptureMode.FILE)

  var imageUri: Uri? by mutableStateOf(null)
  val isImageEmpty get() = imageUri == null

  var bitmap: Bitmap? by mutableStateOf(null)
  val isBitmapEmpty get() = bitmap == null

  val emptyBitmap = createBitmap(1,1)

  fun compositeImage(): Bitmap {

    return bitmap?.let {
      val width = it.width
      val height = it.height
      val diagonal = sqrt(1.0f*width*width + 1.0f*height*height)
      val compositeImage = createBitmap(height * 2, width * 2)
      val canvas = Canvas(compositeImage)
      val matrix: Matrix = Matrix()

      matrix.setTranslate(0f, 0f)
      matrix.postRotate(-135f, height/2f, width/2f)
      canvas.drawBitmap(bitmap ?: emptyBitmap, matrix, null)

      matrix.setTranslate(height/1f, 0f)
      matrix.postRotate(-45f, 1.5f*height, width/2f)
      canvas.drawBitmap(bitmap ?: emptyBitmap, matrix, null)

      matrix.setTranslate(0f, width/1f)
      matrix.postRotate(135f, height/2f, 1.5f*width)
      canvas.drawBitmap(bitmap ?: emptyBitmap, matrix, null)

      matrix.setTranslate(height/1f, width/1f)
      matrix.postRotate(45f, 1.5f*height, 1.5f*width)
      canvas.drawBitmap(bitmap ?: emptyBitmap, matrix, null)

      compositeImage
    } ?: emptyBitmap
  }
}



