package dev.csse.kubiak.demoweek8.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

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