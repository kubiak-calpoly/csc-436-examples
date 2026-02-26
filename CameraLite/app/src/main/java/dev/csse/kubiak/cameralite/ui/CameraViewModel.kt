package dev.csse.kubiak.cameralite.ui

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.CaptureResult
import android.hardware.camera2.TotalCaptureResult
import androidx.camera.camera2.interop.Camera2Interop
import androidx.camera.core.CameraSelector.DEFAULT_FRONT_CAMERA
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.camera.viewfinder.compose.MutableCoordinateTransformer
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.setFrom
import androidx.compose.ui.graphics.toComposeRect
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CameraPreviewViewModel() : ViewModel() {

  private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
  val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest


  @SuppressLint("UnsafeOptInUsageError")
  private val cameraPreviewUseCase = Preview.Builder()
    .apply {
      Camera2Interop.Extender(this)
        .setCaptureRequestOption(
          CaptureRequest.STATISTICS_FACE_DETECT_MODE,
          CaptureRequest.STATISTICS_FACE_DETECT_MODE_FULL
        )
        .setSessionCaptureCallback(object : CameraCaptureSession.CaptureCallback() {
          override fun onCaptureCompleted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            result: TotalCaptureResult
          ) {
            super.onCaptureCompleted(session, request, result)
          }
        })
    }
    .build().apply {
      setSurfaceProvider { newSurfaceRequest ->
        _surfaceRequest.update { newSurfaceRequest }
      }
    }

  suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
    val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
    processCameraProvider.bindToLifecycle(
      lifecycleOwner, DEFAULT_FRONT_CAMERA, cameraPreviewUseCase
    )

    // Cancellation signals we're done with the camera
    try {
      awaitCancellation()
    } finally {
      processCameraProvider.unbindAll()
    }
  }
}

fun List<Rect>.transformToUiCoords(
  transformationInfo: SurfaceRequest.TransformationInfo?,
  uiToBufferCoordinateTransformer: MutableCoordinateTransformer
): List<Rect> = this.map { sensorRect ->
  val bufferToUiTransformMatrix = Matrix().apply {
    setFrom(uiToBufferCoordinateTransformer.transformMatrix)
    invert()
  }

  val sensorToBufferTransformMatrix = Matrix().apply {
    transformationInfo?.let {
      setFrom(it.sensorToBufferTransform)
    }
  }

  val bufferRect = sensorToBufferTransformMatrix.map(sensorRect)
  val uiRect = bufferToUiTransformMatrix.map(bufferRect)

  uiRect
}


