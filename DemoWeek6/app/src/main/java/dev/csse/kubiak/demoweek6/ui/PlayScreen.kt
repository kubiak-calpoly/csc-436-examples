package dev.csse.kubiak.demoweek6.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek6.Division
import dev.csse.kubiak.demoweek6.Loop
import dev.csse.kubiak.demoweek6.R


@Composable
fun PlayScreen(
  loop: Loop,
  modifier: Modifier = Modifier,
  playerViewModel: PlayerViewModel = viewModel()
) {
  val position = playerViewModel.getPosition(loop)

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
        Beat(it)
        if (it.bar > 1 && it.beat == 1) {
          Box(modifier = Modifier.width(1.dp)
            .fillMaxHeight().background(Color(0xff000000)))
        }
      }
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      Column (modifier = Modifier.weight(1f),
        horizontalAlignment =  Alignment.CenterHorizontally ) {
        Text("LOOP",
          style = MaterialTheme.typography.labelSmall)
        Text(position.iteration.toString(),
          style = MaterialTheme.typography.displayLarge )
      }
      IconButton(
        onClick = { }
      ) {
        Icon(
          painterResource(R.drawable.baseline_skip_previous_24),
          contentDescription = "Restart",
          modifier = Modifier.scale(2.5f)
        )
      }
      Spacer(modifier = Modifier.width(20.dp))
      IconButton(
        onClick = { playerViewModel.startPlayer(loop) }
      ) {
        Icon(
          painterResource(R.drawable.baseline_play_circle_24),
          contentDescription = "Play",
          modifier = Modifier.scale(2.5f)
        )
      }
      Spacer(modifier = Modifier.width(20.dp))
      IconButton(
        enabled = false,
        onClick = { }
      ) {
        Icon(
          painterResource(R.drawable.baseline_pause_circle_24),
          contentDescription = "Pause",
          modifier = Modifier.scale(2.5f)
        )
      }
      Column (modifier = Modifier.weight(1f),
        horizontalAlignment =  Alignment.CenterHorizontally ) {
        Text("BAR",
          style = MaterialTheme.typography.labelSmall)
        Text(position.bar.toString(),
          style = MaterialTheme.typography.displayLarge )
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
) {
  val bg = if (hasBeenPlayed)
    MaterialTheme.colorScheme.surfaceDim
  else MaterialTheme.colorScheme.surface

  Box(
    modifier = Modifier
      .background(bg)
      .fillMaxHeight()
  ) {
    Shape(
      lit = isPlaying,
      modifier = Modifier.align(Alignment.BottomCenter)
    )
  }

}

@Composable
fun NumberField(
  labelText: String,
  value: Int?,
  onValueChange: (Int?) -> Unit,
  modifier: Modifier = Modifier
) {
  TextField(
    value = (value ?: "").toString(),
    onValueChange = { v -> onValueChange(v.toIntOrNull()) },
    label = { Text(labelText) },
    singleLine = true,
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Number
    ),
    modifier = modifier,
    textStyle = MaterialTheme.typography.displayLarge
  )
}

@Preview
@Composable
fun PlayScreenDemo() {
  val loop = Loop()

  PlayScreen(loop)
}