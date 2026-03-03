package dev.csse.kubiak.audiodemo.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.audiodemo.audio.AudioPlayer

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun PlayerScreen(
  modifier: Modifier = Modifier,
  model: AudioViewModel = viewModel()
) {
  val context = LocalContext.current
  var filesExpanded by remember { mutableStateOf(false) }
  val menuScrollState = rememberScrollState()


  val player = AudioPlayer(context)
  val filename = model.soundFile

  Column(modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(40.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    ExposedDropdownMenuBox(
      expanded = filesExpanded,
      onExpandedChange = { filesExpanded = !filesExpanded }
    ) {
      TextField(
        label = { Text("Sound") },
        value = filename ?: "(none)",
        onValueChange = {},
        readOnly = true,
        modifier = Modifier.menuAnchor()
      )
      ExposedDropdownMenu(
        expanded = filesExpanded,
        onDismissRequest = { filesExpanded = false },
        modifier = Modifier.scrollable(
          state = menuScrollState,
          orientation = Orientation.Vertical
        )
      ) {
        model.listSounds(context).forEach() { selectedFile ->
          DropdownMenuItem(
            text = { Text(selectedFile) },
            onClick = {
              model.soundFile = selectedFile
              filesExpanded = false
            }
          )
        }
      }
    }

    Row(modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly) {
      Button(
        enabled = filename != null,
        onClick = {
          model.getSoundFile(context, filename?: "")?.let {
            player.start(it)
          }
        }
      ) {
        Text("Play")
      }
      Button(
        enabled = filename != null,
        onClick = {
          player.stop()
        }
      ) {
        Text("Stop")
      }
    }
  }

}