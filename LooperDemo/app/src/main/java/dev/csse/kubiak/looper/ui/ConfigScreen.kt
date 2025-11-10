package dev.csse.kubiak.looper.ui

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Button
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.looper.AudioEngine
import dev.csse.kubiak.looper.Track
import dev.csse.kubiak.looper.data.AppPreferences
import dev.csse.kubiak.looper.data.AppStorage
import kotlinx.coroutines.launch

@Composable
fun ConfigScreen(
  engine: AudioEngine,
  modifier: Modifier = Modifier,
  looperViewModel: LooperViewModel = viewModel()
) {
  val context = LocalContext.current
  val store = AppStorage(context)
  val appPrefs = store.appPreferencesFlow
    .collectAsStateWithLifecycle(initialValue = AppPreferences())
  val coroutineScope = rememberCoroutineScope()
  var filename by remember { mutableStateOf("") }


  val loop = looperViewModel.loop
  Log.d("ConfigScreen", "Loop is now: $loop")
  Column(modifier = modifier) {
    Row {
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Bars in Loop",
        value = appPrefs.value.loopBars,
        onValueChange = { value ->
          coroutineScope.launch() {
            store.saveLoopBars(value)
          }
        }
      )
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Beats per Bar",
        value = appPrefs.value.loopBeats,
        onValueChange = { value ->
          coroutineScope.launch() {
            store.saveLoopBeats(value)
          }
        }
      )
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Subdivisions",
        value = appPrefs.value.loopDivisions,
        onValueChange = { value ->
          coroutineScope.launch() {
            store.saveSubdivisions(value)
          }
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
    Column(
      modifier =
      Modifier.verticalScroll(rememberScrollState())

    ) {
      TrackList(
        looperViewModel.tracks,
        engine,
        onUpdate = { i, t ->
          looperViewModel.updateTrack(i) { t }
        },
        onRemove = { i -> looperViewModel.removeTrack(i) }
      )
      Card(modifier = Modifier.padding(24.dp)) {
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Tracks Filename") },
            value = filename,
            singleLine = true,
            onValueChange = { name: String ->
              filename = name
            }
          )
          Button(
            onClick = {
              looperViewModel.loadTracksFromFile(context, filename)
            }
          ) { Text("Load Tracks") }
        }
      }
    }
  }
}

@Composable
fun TrackList(
  tracks: List<Track>,
  engine: AudioEngine,
  onUpdate: (Int, Track) -> Unit,
  onRemove: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    tracks.forEachIndexed { i, track ->
      TrackView(
        track,
        engine,
        onUpdate = { onUpdate(i, it) },
        onRemove = { onRemove(i)}
      )
    }
  }
}

@Composable
fun TrackView(
  track: Track,
  engine: AudioEngine,
  onUpdate: (Track) -> Unit = {},
  onRemove: () -> Unit
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
          style = MaterialTheme.typography.displaySmall,
          modifier = Modifier.weight(1.0f)
        )
        IconButton(onClick = onRemove) {
          Icon(
            Icons.Filled.Clear,
            contentDescription = "Remove Track"
          )
        }
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