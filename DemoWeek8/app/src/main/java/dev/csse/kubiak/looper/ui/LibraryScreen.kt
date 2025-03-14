package dev.csse.kubiak.looper.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.looper.Loop
import dev.csse.kubiak.looper.Track
import dev.csse.kubiak.looper.data.LoopEntity
import dev.csse.kubiak.looper.data.TrackEntity


@Composable
fun LibraryScreen(
  shouldSave: Boolean = false,
  modifier: Modifier = Modifier.Companion,
  onLoopLoaded: (Loop) -> Unit = {},
  looperViewModel: LooperViewModel,
  libraryViewModel: LibraryViewModel = viewModel(
    factory = LibraryViewModel.Companion.Factory
  ),
) {
  var showAddLoopDialog by rememberSaveable {
    mutableStateOf(shouldSave)
  }
  val uiState = libraryViewModel.uiState.collectAsStateWithLifecycle()
  val loopList = uiState.value.loopList
  val selectedLoopId = uiState.value.selectedLoopId
  val trackList = uiState.value.trackList

  if (showAddLoopDialog) {
    val loop = looperViewModel.loop
    val tracks = looperViewModel.tracks
    AddLoopDialog(
      onConfirmation = { title ->
        showAddLoopDialog = false
        libraryViewModel.addLoop(
          title,
          barsToLoop = loop.barsToLoop,
          beatsPerBar = loop.beatsPerBar,
          subdivisions = loop.subdivisions,
          tracks = tracks
        )
      },
      onDismissRequest = {
        showAddLoopDialog = false
      }
    )
  }

  LazyVerticalStaggeredGrid(
    columns = StaggeredGridCells.Adaptive(minSize = 128.dp),
    contentPadding = PaddingValues(12.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalItemSpacing = 8.dp,
    modifier = modifier
  ) {
    items(
      loopList,
      key = { it.id }
    ) { loop ->
      LoopCard(loop,
        trackList,
        isSelected = loop.id == selectedLoopId,
        onSelect = { loop ->
          libraryViewModel.selectLoop(loop)
        },
        onLoad = { selected ->
          looperViewModel.loop =
            Loop(
              barsToLoop = selected.barsToLoop,
              beatsPerBar = selected.beatsPerBar,
              subdivisions = selected.subdivisions
            )
          Log.d(
            "LibraryScreen",
            "Loaded new Loop: $selected"
          )
          val tracks = trackList.map { entity ->
            Track(name = entity.name).apply {
              setSound(entity.soundFile)
              parseString(entity.data)
            }
          }

          looperViewModel.tracks.clear()
          looperViewModel.tracks.addAll(tracks)
          Log.d(
            "LibraryScreen",
            "Loaded new tracks: $tracks"
          )
          onLoopLoaded(looperViewModel.loop)
        }
      )

    }
  }
}

@Composable
fun LoopCard(
  loop: LoopEntity,
  tracks: List<TrackEntity>,
  isSelected: Boolean = false,
  onSelect: (LoopEntity) -> Unit = {},
  onLoad: (LoopEntity) -> Unit = {}
) {
  Card(modifier = Modifier.Companion.clickable(
    enabled = !isSelected,
    onClick = { onSelect(loop) }
  )) {
    Column(modifier = Modifier.Companion.padding(8.dp)) {
      Text(
        loop.title,
        style = MaterialTheme.typography.displaySmall,
        textAlign = TextAlign.Companion.Center
      )
      Row(
        modifier = Modifier.Companion.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column(
          modifier = Modifier.Companion.weight(1f),
          horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
          Text(
            "Bars",
            style = MaterialTheme.typography.labelSmall
          )
          Text(
            loop.barsToLoop.toString(),
            style = MaterialTheme.typography.displaySmall
          )
        }
        Column(
          modifier = Modifier.Companion.weight(1f),
          horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
          Text(
            "Beats",
            style = MaterialTheme.typography.labelSmall
          )
          Text(
            loop.beatsPerBar.toString(),
            style = MaterialTheme.typography.displaySmall
          )
        }
        Column(
          modifier = Modifier.Companion.weight(1f),
          horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
          Text(
            "Subdiv",
            style = MaterialTheme.typography.labelSmall
          )
          Text(
            loop.subdivisions.toString(),
            style = MaterialTheme.typography.displaySmall
          )

        }
      }
      if (isSelected) {
        tracks.forEach { track ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              "${track.name}:",
              style = MaterialTheme.typography.labelSmall
            )
            Text(
              track.soundFile,
              style = MaterialTheme.typography.bodySmall
            )
          }
        }
        Button(
          modifier = Modifier.Companion.fillMaxWidth(),
          onClick = {
            onLoad(loop)
          }) { Text("Load") }
      }
    }
  }
}

@Composable
fun AddLoopDialog(
  onConfirmation: (String) -> Unit,
  onDismissRequest: () -> Unit,
) {
  var loopTitle by remember { mutableStateOf("") }

  AlertDialog(
    onDismissRequest = {
      onDismissRequest()
    },
    title = {
      TextField(
        label = { Text("Loop name") },
        value = loopTitle,
        onValueChange = { loopTitle = it },
        singleLine = true,
        keyboardActions = KeyboardActions(
          onDone = {
            onConfirmation(loopTitle)
          }
        )
      )
    },
    confirmButton = {
      Button(
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.primary
        ),
        onClick = {
          onConfirmation(loopTitle)
        }) {
        Text(text = "Add")
      }
    },
    dismissButton = {
      Button(
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.secondary
        ),
        onClick = {
          onDismissRequest()
        }) {
        Text(text = "Cancel")
      }
    },
  )
}