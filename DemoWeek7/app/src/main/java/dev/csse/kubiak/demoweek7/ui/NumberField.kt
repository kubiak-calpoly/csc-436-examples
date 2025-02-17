package dev.csse.kubiak.demoweek7.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NumberField(
  labelText: String,
  value: Int,
  onValueChange: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  var stringValue by remember { mutableStateOf(value.toString()) }

  TextField(
    value = stringValue,
    onValueChange = { v ->
      stringValue = v
      val intValue = v.toIntOrNull()
      if (intValue != null) onValueChange(intValue)
    },
    label = { Text(labelText) },
    singleLine = true,
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Number
    ),
    modifier = modifier,
    textStyle = MaterialTheme.typography.displayLarge
  )
}