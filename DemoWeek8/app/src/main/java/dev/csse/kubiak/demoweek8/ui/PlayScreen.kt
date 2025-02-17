package dev.csse.kubiak.demoweek8.ui

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek8.Division
import dev.csse.kubiak.demoweek8.Loop
import dev.csse.kubiak.demoweek8.R

@Composable
fun PlayScreen(
  loop: Loop,
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
      loop.beats.forEach {
        Beat(
          it,
          hasBeenPlayed = it.beat < position.beat ||
                  it.beat == position.beat &&
                  it.subdivision < position.subdivision,
          isPlaying = playerViewModel.isRunning &&
                  it.beat == position.beat &&
                  it.subdivision == position.subdivision,
          modifier = Modifier.weight(1f)
        )
      }
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
  beat: Division,
  hasBeenPlayed: Boolean = false,
  isPlaying: Boolean = false,
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
  }

}





@Preview
@Composable
fun PlayScreenDemo() {
  val loop = Loop()

  PlayScreen(loop)
}