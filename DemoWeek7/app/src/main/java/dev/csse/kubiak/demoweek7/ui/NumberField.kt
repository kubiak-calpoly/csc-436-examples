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
  var isNull by remember { mutableStateOf(false) }
  var stringValue = if ( !isNull ) value.toString() else ""

  TextField(
    value = stringValue,
    onValueChange = { v ->
      val intValue = v.toIntOrNull()
      if (intValue == null) {
        isNull = true
      } else {
        onValueChange(intValue)
        isNull = false
      }
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