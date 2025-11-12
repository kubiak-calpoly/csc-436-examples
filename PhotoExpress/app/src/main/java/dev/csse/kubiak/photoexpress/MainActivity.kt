package dev.csse.kubiak.photoexpress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dev.csse.kubiak.photoexpress.ui.PhotoExpressApp
import dev.csse.kubiak.photoexpress.ui.theme.PhotoExpressTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {

         PhotoExpressTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               PhotoExpressApp()
            }
         }
      }
   }
}
