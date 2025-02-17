package dev.csse.kubiak.demoweek8.ui

import android.R
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek8.AudioEngine
import dev.csse.kubiak.demoweek8.Track

@Composable
fun ConfigScreen(
  engine: AudioEngine,
  modifier: Modifier = Modifier,
  looperViewModel: LooperViewModel = viewModel()
) {
  val loop = looperViewModel.loop

  Column(modifier = modifier) {
    Row {
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Number of Bars",
        value = loop.barsToLoop,
        onValueChange = { value ->
          loop.barsToLoop = value ?: 4
        }
      )
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Beats per Bar",
        value = loop.beatsPerBar,
        onValueChange = { value ->
          loop.beatsPerBar = value ?: 4
        }
      )
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Subdivisions",
        value = loop.barsToLoop,
        onValueChange = { value ->
          loop.barsToLoop = value ?: 4
        }
      )
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        "Tracks (${looperViewModel.tracks.size})",
        style = MaterialTheme.typography.displaySmall
      )
      IconButton(
        onClick = { looperViewModel.addTrack() }
      ) {
        Icon(
          Icons.Outlined.AddCircle,
          contentDescription = "Add track",
          modifier = Modifier.scale(1.5f)

        )
      }
    }
    TrackList(
      looperViewModel.tracks,
      engine,
      onUpdate = { i, t ->
        looperViewModel.updateTrack(i) {t}
      },
    )
  }
}

@Composable
fun TrackList(
  tracks: List<Track>,
  engine: AudioEngine,
  onUpdate: (Int, Track) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    tracks.forEachIndexed { i, track ->
      TrackView(
        track,
        engine,
        onUpdate = { onUpdate(i, it) }
      )
    }
  }
}

@Composable
fun TrackView(
  track: Track,
  engine: AudioEngine,
  onUpdate: (Track) -> Unit = {}
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(4.dp)
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(modifier = Modifier.fillMaxWidth()) {
        Text(
          track.name,
          style = MaterialTheme.typography.displaySmall
        )
      }
      SoundSelectorField(
        track.sound,
        engine.listAudioAssets(),
        onSelect = { sound ->
          track.sound = sound
          onUpdate(track)
        }
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundSelectorField(
  sound: String?,
  options: List<String> = listOf<String>(),
  onSelect: (String) -> Unit = {},
  modifier: Modifier = Modifier
) {
  var expanded by remember { mutableStateOf(false) }
  var selectedOptionText by remember { mutableStateOf(sound) }
  var menuScrollState = rememberScrollState()

  ExposedDropdownMenuBox(
    expanded = expanded,
    onExpandedChange = { expanded = !expanded },
  ) {
    TextField(
      label = { Text("Sound File") },
      value = selectedOptionText ?: "(none)",
      onValueChange = {},
      readOnly = true,
      modifier = Modifier.menuAnchor()
    )
    ExposedDropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false },
      modifier = Modifier.scrollable(
        state = menuScrollState,
        orientation = Orientation.Vertical
      )
    ) {
      options.forEach() { selectedOption ->
        DropdownMenuItem(
          text = { Text(selectedOption) },
          onClick = {
            selectedOptionText = selectedOption
            expanded = false
            onSelect(selectedOption)
          }
        )
      }
    }
  }

}