package dev.csse.kubiak.looper.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.csse.kubiak.looper.R
import dev.csse.kubiak.looper.AudioEngine
import dev.csse.kubiak.looper.Loop

@Composable
fun PlayerWidget(
  engine: AudioEngine,
  playerViewModel: PlayerViewModel,
  looperViewModel: LooperViewModel,
) {
  val context = LocalContext.current
  val position: Loop.Position by
     playerViewModel.positionState.collectAsStateWithLifecycle()

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
        engine.prepare(looperViewModel.tracks) {
          tracks -> playerViewModel
            .startPlayer(context, looperViewModel.loop) {
              engine.playAtPosition(it, tracks)
          }
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
