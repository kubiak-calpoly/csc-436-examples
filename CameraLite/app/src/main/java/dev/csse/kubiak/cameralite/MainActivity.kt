package dev.csse.kubiak.cameralite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.common.util.concurrent.ListenableFuture
import dev.csse.kubiak.cameralite.ui.CameraPreview
import dev.csse.kubiak.cameralite.ui.theme.CameraLiteTheme

class MainActivity : ComponentActivity() {
   private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      cameraProviderFuture = ProcessCameraProvider.getInstance(this)

      setContent {

         CameraLiteTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               CameraPreview()
            }
         }
      }
   }
}
