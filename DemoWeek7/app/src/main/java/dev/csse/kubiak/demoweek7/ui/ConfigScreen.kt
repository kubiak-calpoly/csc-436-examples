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
import dev.csse.kubiak.demoweek7.Track

@Composable
fun ConfigScreen(
  modifier: Modifier = Modifier,
  looperViewModel: LooperViewModel = viewModel()
) {
  var loop = looperViewModel.loop

  val store = AppStorage(LocalContext.current)
  val appPrefs = store.appPreferencesFlow
    .collectAsStateWithLifecycle(AppPreferences())

  Column(modifier = modifier) {
    Row {
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Number of Bars",
        value = loop.barsToLoop,
        onValueChange = { value ->
          looperViewModel.loop = loop.copy(barsToLoop = value ?: 1)
        }
      )
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Beats per Bar",
        value = loop.beatsPerBar,
        onValueChange = { value ->
          looperViewModel.loop = loop.copy(beatsPerBar = value ?: 4)
        }
      )
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Subdivisions",
        value = loop.subdivisions,
        onValueChange = { value ->
          looperViewModel.loop = loop.copy(subdivisions = value ?: 2)
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
