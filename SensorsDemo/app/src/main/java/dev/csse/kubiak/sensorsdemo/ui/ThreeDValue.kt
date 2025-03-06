package dev.csse.kubiak.sensorsdemo.ui

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ThreeDValue(
  values: List<Float>,
  modifier: Modifier = Modifier,
  labels: List<String> = listOf("X", "Y", "Z"),
) {

  Column(modifier = modifier) {
    values.forEachIndexed { index, value ->
      Row(modifier = Modifier.fillMaxWidth()) {
        Text(
          labels[index],
          modifier = Modifier.weight(1f),
          style = MaterialTheme.typography.labelLarge
        )
        Text(
          String.format("%.6f",value),
          modifier = Modifier.weight(7f),
          style = MaterialTheme.typography.displayLarge,
          textAlign = TextAlign.Right
        )
      }
    }
  }
}
