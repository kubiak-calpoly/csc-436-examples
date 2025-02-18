package dev.csse.kubiak.demoweek7.ui


import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import dev.csse.kubiak.demoweek7.AppPreferences
import dev.csse.kubiak.demoweek7.AppStorage
import kotlinx.coroutines.launch

@Composable
fun ConfigScreen(
  modifier: Modifier = Modifier,
) {
  val store = AppStorage(LocalContext.current)
  val appPrefs = store.appPreferencesFlow
    .collectAsStateWithLifecycle(initialValue = AppPreferences())
  val coroutineScope = rememberCoroutineScope()

  Column(modifier = modifier) {
    Row {
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Number of Bars",
        value = appPrefs.value.loopBars,
        onValueChange = { value ->
          coroutineScope.launch {
            store.saveLoopBars(value)
          }
        }
      )
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Beats per Bar",
        value = appPrefs.value.loopBeats,
        onValueChange = { value ->
          coroutineScope.launch {
            store.saveLoopBeats(value)
          }
        }
      )
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Subdivisions",
        value = appPrefs.value.loopDivisions,
        onValueChange = { value ->
          coroutineScope.launch {
            store.saveSubdivisions(value)
          }
        }
      )
    }
//    Row(
//      modifier = Modifier.fillMaxWidth(),
//      verticalAlignment = Alignment.CenterVertically,
//      horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//      Text(
//        "Tracks (${looperViewModel.tracks.size})",
//        style = MaterialTheme.typography.displaySmall
//      )
//      IconButton(
//        onClick = { looperViewModel.addTrack() }
//      ) {
//        Icon(
//          Icons.Outlined.AddCircle,
//          contentDescription = "Add track",
//          modifier = Modifier.scale(1.5f)
//
//        )
//      }
//    }
//    TrackList(
//      looperViewModel.tracks,
//      onUpdate = { i, t ->
//        looperViewModel.updateTrack(i) {t}
//      },
//    )
  }
}
//
//@Composable
//fun TrackList(
//  tracks: List<Track>,
//  onUpdate: (Int, Track) -> Unit,
//  modifier: Modifier = Modifier
//) {
//  Column(modifier = modifier) {
//    tracks.forEachIndexed { i, track ->
//      TrackView(track)
//    }
//  }
//}
//
//@Composable
//fun TrackView(
//  track: Track,
//) {
//  Card(
//    modifier = Modifier
//      .fillMaxWidth()
//      .padding(4.dp)
//  ) {
//    Column(modifier = Modifier.padding(12.dp)) {
//      Row(modifier = Modifier.fillMaxWidth()) {
//        Text(
//          track.name,
//          style = MaterialTheme.typography.displaySmall
//        )
//      }
//    }
//  }
//}
