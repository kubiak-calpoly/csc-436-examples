package dev.csse.kubiak.demoweek7.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambdaInstance
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek7.Loop
import dev.csse.kubiak.demoweek7.R
import dev.csse.kubiak.demoweek7.Track

@Composable
fun PlayScreen(
  loop: Loop,
  tracks: List<Track> = listOf<Track>(),
  modifier: Modifier = Modifier,
  playerViewModel: PlayerViewModel = viewModel()
) {
  val position: Loop.Position  by playerViewModel
    .positionState.collectAsStateWithLifecycle()


  Column(modifier = modifier.fillMaxWidth()) {
    Row(modifier = Modifier.fillMaxWidth()) {
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Number of Iterations",
        value = playerViewModel.iterations,
        onValueChange = { value ->
          playerViewModel.iterations = value ?: 1
        }
      )
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Beats Per Minute",
        value = playerViewModel.bpm,
        onValueChange = { value ->
          playerViewModel.bpm = value ?: 60
        }
      )
    }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(12.dp, 40.dp),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      loop.forEachTick { bar, beat, subdivision ->
        Beat(
          hasBeenPlayed = bar < position.bar ||
                  bar == position.bar && (
                  beat < position.beat ||
                  beat == position.beat &&
                  subdivision < position.subdivision),
          isPlaying = playerViewModel.isRunning &&
                  bar == position.bar &&
                  beat == position.beat &&
                  subdivision == position.subdivision,
          modifier = Modifier.weight(1f),
          trackData = tracks.map { track: Track ->
            val pos = Track.Position(
              bar = bar, beat = beat, subdivision = subdivision
            )
            track.getHit(pos)?.volume
          }
        )
      }
    }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      PlayerPosition(position, modifier = Modifier.weight(1f))
      PlayerControls(
        isRunning = playerViewModel.isRunning,
        onPlay =  { playerViewModel.startPlayer(loop) },
        onPause =  { playerViewModel.pausePlayer() },
        onReset = { playerViewModel.resetPlayer() },
        modifier = Modifier.weight(1f))
    }

  }

}

@Composable
fun Shape(
  lit: Boolean = false,
  modifier: Modifier = Modifier
) {
  val icon = if (lit)
    R.drawable.baseline_square_24
  else R.drawable.outline_square_24
  val text = if (lit) "*" else "-"

  return Icon(
    painter = painterResource(icon),
    contentDescription = text,
    modifier = modifier
  )
}

@Composable
fun Beat(
  hasBeenPlayed: Boolean = false,
  isPlaying: Boolean = false,
  trackData: List<Float?> = listOf(),
  modifier: Modifier = Modifier
) {
  val bg = if (hasBeenPlayed)
    MaterialTheme.colorScheme.surfaceDim
  else MaterialTheme.colorScheme.surface

  Box(
    modifier = modifier
      .background(bg)
      .fillMaxHeight()
  ) {
    Shape(
      lit = isPlaying,
      modifier = Modifier.align(Alignment.BottomCenter)
        .padding(0.dp, 8.dp)
    )
    trackData.forEachIndexed { i, hit ->
      if (hit != null) {
        Shape(
          lit = isPlaying || hasBeenPlayed,
          modifier = Modifier.align(Alignment.BottomCenter)
            .padding(bottom = (32 + 16*i).dp)
        )
      }
    }
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



@Preview
@Composable
fun PlayScreenDemo() {
  val loop = Loop()

  PlayScreen(loop)
}