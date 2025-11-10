package dev.csse.kubiak.looper.ui

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.looper.AudioPlayer
import dev.csse.kubiak.looper.AudioRecorder
import kotlin.math.ceil

@Composable
fun SamplerScreen(
  modifier: Modifier = Modifier,
  samplerViewModel: SamplerViewModel = viewModel()
) {
  val context = LocalContext.current
  val player = AudioPlayer(context)
  val recorder = AudioRecorder(context)
  val sampleFile = samplerViewModel.sampleFile
  val duration = samplerViewModel.durationMs
  val countIn = samplerViewModel.countInMs
  val progress = samplerViewModel.progressMs
  val isPlaying = samplerViewModel.isPlaying
  val isRecording = samplerViewModel.isRecording

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    TextField(
      modifier = Modifier.fillMaxWidth(),
      label = { Text("Filename") },
      value = sampleFile ?: "",
      singleLine = true,
      onValueChange = { name: String ->
        samplerViewModel.sampleFile = name
      }
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Duration (ms)",
        value = duration,
        onValueChange = {
          samplerViewModel.durationMs = it
        }
      )

      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          modifier = Modifier.fillMaxWidth(),
          enabled = sampleFile != null,
          onClick = {
            recorder.prepareRecorder(sampleFile!!)
            samplerViewModel.sample(recorder)
          }
        ) {
          Text("Record")
        }

        Button(
          modifier = Modifier.fillMaxWidth(),
          enabled = sampleFile != null,
          onClick = {
            player.preparePlayer(sampleFile!!)
            samplerViewModel.playback(player)
          }
        ) {
          Text("Replay")
        }
      }
    }

    if (countIn > 0) {
      Text("${ceil(1.0f * (countIn - 1) / 1000).toInt()}",
        modifier = Modifier.align(Alignment.CenterHorizontally),
        style = TextStyle(fontSize = 20.em)
      )
    }

    if (isPlaying || isRecording ) {
      Box(
        modifier = Modifier
          .height(100.dp)
          .align(Alignment.Start)
          .fillMaxWidth(
            1f * progress / duration
          )
          .background(color =
            if (isPlaying) Color.Green else Color.Red
          )
          .padding(8.dp)
      ) {
        Text(
          if (isPlaying) "Playing…"
          else "Recording…",
          color= Color.White,
          modifier = Modifier.align(Alignment.CenterStart)
        )
      }
    }
  }
}
