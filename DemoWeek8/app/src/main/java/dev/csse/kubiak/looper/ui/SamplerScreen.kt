package dev.csse.kubiak.looper.ui

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
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.looper.AudioPlayer
import dev.csse.kubiak.looper.AudioRecorder

@Composable
fun SamplerScreen(
  modifier: Modifier = Modifier,
  samplerViewModel: SamplerViewModel = viewModel()
) {
  val context = LocalContext.current
  val player = AudioPlayer(context)
  val recorder = AudioRecorder(context)
  val sampleFile = samplerViewModel.sampleFile

  Column(modifier = modifier.fillMaxSize()) {
    TextField(
      modifier = Modifier.fillMaxWidth(),
      label = { Text("Sound Sample Filename") },
      value = sampleFile ?: "",
      onValueChange = { name: String ->
        samplerViewModel.sampleFile = name
      }
    )

    Row(modifier = Modifier.fillMaxWidth() ) {
      Button(
        enabled = sampleFile != null,
        modifier = Modifier.weight(1f),
        onClick = {
          player.start(sampleFile!!)
        }
      ) {
        Text("Play")
      }
      Button(
        enabled = sampleFile != null,
        modifier = Modifier.weight(1f),
        onClick = {
          recorder.stop()
          player.stop()
        }
      ) {
        Text("Stop")
      }
      Button(
        enabled = sampleFile != null,
        modifier = Modifier.weight(1f),
        onClick = {
          recorder.start(sampleFile!!)
        }
      ) {
        Text("Record")
      }
    }
  }

}