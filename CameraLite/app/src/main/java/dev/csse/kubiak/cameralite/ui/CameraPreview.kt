package dev.csse.kubiak.cameralite.ui

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.launch

@Composable
fun CameraPreview(
   modifier: Modifier = Modifier,
   onUseCase: (UseCase) -> Unit = { },
   scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
) {
   val coroutineScope = rememberCoroutineScope()
   val lifecycleOwner = LocalLifecycleOwner.current
   AndroidView(
      modifier = modifier,
      factory = { context ->
         val previewView = PreviewView(context).apply {
            this.scaleType = scaleType
            layoutParams = ViewGroup.LayoutParams(
               ViewGroup.LayoutParams.MATCH_PARENT,
               ViewGroup.LayoutParams.MATCH_PARENT
            )
         }

         onUseCase(Preview.Builder()
            .build()
            .also {
               it.setSurfaceProvider(previewView.surfaceProvider)
            }
         )
         previewView
      }
   )
}