package dev.csse.kubiak.looper.ui

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.looper.AudioEngine
import dev.csse.kubiak.looper.Loop
import dev.csse.kubiak.looper.R
import dev.csse.kubiak.looper.Track

@Composable
fun PlayScreen(
  looperViewModel: LooperViewModel,
  engine: AudioEngine,
  modifier: Modifier = Modifier,
  playerViewModel: PlayerViewModel = viewModel()
) {
  val loop = looperViewModel.loop
  val tracks = looperViewModel.tracks
  val position: Loop.Position by playerViewModel
    .positionState.collectAsStateWithLifecycle()
  Log.d("PlayScreen", "Play Screen has $tracks tracks")


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
        if (bar > 1 && beat == 1 && subdivision == 1) {
          Box(
            modifier = Modifier
              .width(1.dp)
              .fillMaxHeight()
              .background(Color(0xff000000))
          )
        }
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
    PlayerWidget(engine, playerViewModel, looperViewModel)
  }

}

@Composable
fun Shape(
  hit: Boolean,
  lit: Boolean = false,
  modifier: Modifier = Modifier
) {
  val icon = if (hit)
    R.drawable.baseline_square_24
  else R.drawable.outline_square_24
  val text = if (hit) "*" else "-"

  return Icon(
    painter = painterResource(icon),
    tint = if (lit) Color(0xff000000) else Color(0x11000000),
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
      hit = isPlaying,
      lit = hasBeenPlayed,
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(0.dp, 8.dp)
    )
    trackData.forEachIndexed { i, hit ->
      Shape(
        hit != null,
        lit = isPlaying || hasBeenPlayed,
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(bottom = (32 + 24 * i).dp)
      )
    }
  }
}






