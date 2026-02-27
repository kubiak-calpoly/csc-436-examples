package dev.csse.kubiak.cameralite.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.net.Uri
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.core.graphics.createBitmap
import androidx.lifecycle.ViewModel
import java.io.ByteArrayOutputStream
import kotlin.math.sqrt
import androidx.core.net.toUri


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


  fun saveImage(context: Context): Uri? {
    val image = bitmap ?: emptyBitmap
    val bytes = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path =
      MediaStore.Images.Media.insertImage(
        context.getContentResolver(), image, "Composite Image", null
      )
    return path.toUri()
  }


  fun compositeImage(): Bitmap {

    return bitmap?.let {
      val width = it.width
      val height = it.height
      val diagonal = sqrt(1.0f*width*width + 1.0f*height*height)
      val compositeImage = createBitmap(height * 2, width * 2)
      val canvas = Canvas(compositeImage)
      val matrix: Matrix = Matrix()

      canvas.drawRect(Rect(0, 0, 2*height, 2*width), Paint(Color.BLACK))

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



