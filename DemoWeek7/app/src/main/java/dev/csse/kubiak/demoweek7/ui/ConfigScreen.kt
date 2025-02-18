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
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@Composable
fun ConfigScreen(
  modifier: Modifier = Modifier,
  looperViewModel: LooperViewModel = viewModel()
) {
  val loop = looperViewModel.loop

  Column(modifier = modifier) {
    Row {
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Number of Bars",
        value = loop.barsToLoop,
        onValueChange = { value ->
          looperViewModel.loop = loop.copy(barsToLoop = value)
        }
      )
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Beats per Bar",
        value = loop.beatsPerBar ,
        onValueChange = { value ->
          looperViewModel.loop = loop.copy(beatsPerBar = value)
        }
      )
      NumberField(
        modifier = Modifier.weight(1f),
        labelText = "Subdivisions",
        value = loop.subdivisions,
        onValueChange = { value ->
          looperViewModel.loop = loop.copy(subdivisions = value)
        }
      )
    }

  }
}
