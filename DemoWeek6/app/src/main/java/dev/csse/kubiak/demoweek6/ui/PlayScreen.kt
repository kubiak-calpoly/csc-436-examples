package dev.csse.kubiak.demoweek6.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek6.Division
import dev.csse.kubiak.demoweek6.Loop
import dev.csse.kubiak.demoweek6.R

@Composable
fun PlayScreen(
  loop: Loop,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Row(modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly) {
      loop.beats.forEach {
        Beat(it)
      }
    }
  }

}

@Composable
fun Shape(lit: Boolean = false) {
  val icon = if (lit)
    R.drawable.baseline_square_24
  else R.drawable.outline_square_24
  val text = if (lit) "*" else "-"

  return Icon(
    painter = painterResource(icon),
    contentDescription = text
  )
}

@Composable
fun Beat(
  beat: Division,
  hasBeenPlayed: Boolean = false,
  isPlaying: Boolean = false
) {
  val bg = if (hasBeenPlayed)
    MaterialTheme.colorScheme.primary
  else MaterialTheme.colorScheme.surface

  Box(modifier = Modifier.background(bg)) {
    Shape(lit = isPlaying)
  }

}

@Preview
@Composable
fun PlayScreenDemo() {
  val loop = Loop()

  PlayScreen(loop)
}