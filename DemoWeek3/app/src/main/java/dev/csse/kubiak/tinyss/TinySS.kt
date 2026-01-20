package dev.csse.kubiak.tinyss

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

val columnWidth = 16.dp * 5
val rowHeaderWidth = 16.dp * 1

@Preview(
  backgroundColor = 0xff222222,
  showBackground = true )
@Composable
fun TinySSPreview() {
  TinySS()
}

@Composable
fun TinySS() {
  val columnNames = listOf("A", "B", "C")
  val rowNumbers = listOf(1, 2, 3)

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(24.dp)
  ) {
    ColumnHeader(columnNames)
    for (row in rowNumbers) {
      RowData(row, columnNames)
    }
  }
}

@Composable
fun ColumnHeader(columnNames: List<String>) {
  Row(verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth() ) {
    Text(
      "",
      modifier = Modifier.width(rowHeaderWidth)
    )
    for (col in columnNames) {
      Text(
        text = col,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.width(columnWidth),
        color = MaterialTheme.colorScheme.onPrimary
      )
    }
  }
}

@Composable
fun RowData(row: Int, columnNames: List<String>) {
  Row(verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth()) {
    Text(
      row.toString(),
      style = MaterialTheme.typography.labelLarge,
      textAlign = TextAlign.Right,
      modifier = Modifier.width(rowHeaderWidth),
      color = MaterialTheme.colorScheme.onPrimary
    )
    for ( col in columnNames ) {
      Cell(
        col, row,
        modifier = Modifier.width(columnWidth)
      )
    }
  }
}

@Composable
fun Cell(
  col: String,
  row: Int,
  modifier: Modifier = Modifier
) {
  val data = null // TODO get data(col, row)

  TextField(
    value = (data ?: "").toString(),
    onValueChange = {
      // TODO set data(col, row)
    },
    singleLine = true,
    textStyle = TextStyle(
      fontSize = 20.sp,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.End,
    ),
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Number
    ),
    modifier = modifier
  )
}

