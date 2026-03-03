package dev.csse.kubiak.audiodemo.ui

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
import dev.csse.kubiak.audiodemo.audio.AudioPlayer

@Composable
fun PlayerScreen(
  modifier: Modifier = Modifier,
  model: AudioViewModel = viewModel()
) {
  val context = LocalContext.current
  val player = AudioPlayer(context)
  val file = model.soundFile

  Column(modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(40.dp)
  ) {
    TextField(
      modifier = Modifier.fillMaxWidth(),
      label = { Text("Sound Sample Filename") },
      value = file ?: "",
      onValueChange = { name: String ->
        model.soundFile = name
      }
    )

    Row(modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly) {
      Button(
        enabled = file != null,
        modifier = Modifier.weight(1f),
        onClick = {
          player.start(file!!)
        }
      ) {
        Text("Play")
      }
      Button(
        enabled = file != null,
        modifier = Modifier.weight(1f),
        onClick = {
          player.stop()
        }
      ) {
        Text("Stop")
      }
    }
  }

}