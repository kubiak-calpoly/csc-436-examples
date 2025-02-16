package dev.csse.kubiak.demoweek8.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek8.AudioEngine
import dev.csse.kubiak.demoweek8.Loop
import dev.csse.kubiak.demoweek8.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AudioPlayer(
  loop: Loop,
  engine: AudioEngine,
  playerViewModel: PlayerViewModel = viewModel()
) {
  val context = LocalContext.current
  var position by remember { mutableStateOf(Loop.Position()) }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    PlayerPosition(position, modifier = Modifier.weight(1f))
    PlayerControls(
      isRunning = playerViewModel.isRunning,
      onPlay = {
        engine.prepare(loop)
        playerViewModel.startPlayer(context, loop) {
          engine.playAtPosition(it)
        }
      },
      onPause = { playerViewModel.pausePlayer() },
      onReset = { playerViewModel.resetPlayer() },
      modifier = Modifier.weight(1f)
    )
  }

}

@Composable
fun PlayerPosition(
  position: Loop.Position,
  modifier: Modifier = Modifier
) {
  Text(
    "${position.iteration}:" +
            "${position.bar}.${position.beat}.${position.subdivision}",
    style = MaterialTheme.typography.displayMedium,
    textAlign = TextAlign.Center,
    modifier = modifier.border(
      width = 2.dp,
      color = MaterialTheme.colorScheme.primary
    )
  )
}

@Composable
fun PlayerControls(
  isRunning: Boolean = false,
  onPlay: () -> Unit = {},
  onPause: () -> Unit = {},
  onReset: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.SpaceEvenly,
    verticalAlignment = Alignment.CenterVertically
  ) {
    IconButton(
      onClick = onReset
    ) {
      Icon(
        painterResource(R.drawable.baseline_skip_previous_24),
        contentDescription = "Restart",
        modifier = Modifier.scale(2.5f)
      )
    }
    IconButton(
      enabled = !isRunning,
      onClick = onPlay
    ) {
      Icon(
        painterResource(R.drawable.baseline_play_circle_24),
        contentDescription = "Play",
        modifier = Modifier.scale(2.5f)
      )
    }
    IconButton(
      enabled = isRunning,
      onClick = onPause
    ) {
      Icon(
        painterResource(R.drawable.baseline_pause_circle_24),
        contentDescription = "Pause",
        modifier = Modifier.scale(2.5f)
      )
    }
  }
}
