package dev.csse.kubiak.audiodemo.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.audiodemo.audio.AudioRecorder

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RecorderScreen(
  modifier: Modifier = Modifier,
  model: AudioViewModel = viewModel()
) {
  val context = LocalContext.current
  val recorder = AudioRecorder(context)
  val filename = model.soundFile

  Column(modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(40.dp)
  ) {
    TextField(
      modifier = Modifier.fillMaxWidth(),
      label = { Text("Sound Sample Filename") },
      value = filename ?: "",
      onValueChange = { name: String ->
        model.soundFile = name
      }
    )

    Row(modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly) {
      Button(
        enabled = filename != null,
        modifier = Modifier.weight(1f),
        onClick = {
          model.getSoundFile(context, filename?: "")?.let {
            recorder.start(it)

          }
        }
      ) {
        Text("Record")
      }
      Button(
        enabled = filename != null,
        modifier = Modifier.weight(1f),
        onClick = {
          recorder.stop()
        }
      ) {
        Text("Stop")
      }
    }
  }

}