package dev.csse.kubiak.demoweek7.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek7.data.LoopEntity

@Composable
fun LibraryScreen(
  shouldSave: Boolean = false,
  modifier: Modifier = Modifier,
  looperViewModel: LooperViewModel = viewModel(),
  libraryViewModel: LibraryViewModel = viewModel(
    factory = LibraryViewModel.Factory
  ),
) {
  var showAddLoopDialog by rememberSaveable {
    mutableStateOf(shouldSave)
  }
  val uiState = libraryViewModel.uiState.collectAsStateWithLifecycle()
  val loopList = uiState.value.loopList

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

  LazyVerticalGrid(
    columns = GridCells.Adaptive(minSize = 128.dp),
    contentPadding = PaddingValues(12.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    modifier = modifier
  ) {
    items(
      loopList,
      key = { it.id }
    ) { loop ->
      LoopCard(loop)
    }
  }
}

@Composable
fun LoopCard(loop: LoopEntity) {
  Card {
    Column(modifier = Modifier.padding(8.dp)) {
      Text(
        loop.title,
        style = MaterialTheme.typography.displaySmall,
        textAlign = TextAlign.Center
      )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column(
          modifier = Modifier.weight(1f),
          horizontalAlignment = Alignment.CenterHorizontally
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
          modifier = Modifier.weight(1f),
          horizontalAlignment = Alignment.CenterHorizontally
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
          modifier = Modifier.weight(1f),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            "Bars",
            style = MaterialTheme.typography.labelSmall
          )
          Text(
            loop.subdivisions.toString(),
            style = MaterialTheme.typography.displaySmall
          )
        }

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