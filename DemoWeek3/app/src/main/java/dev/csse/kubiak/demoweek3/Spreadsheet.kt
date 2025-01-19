package dev.csse.kubiak.demoweek3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.callbackFlow

val fontSize = 24.sp
val columnWidth = 16.dp * 5
val rowHeaderWidth = 16.dp * 3

@Preview
@Composable
fun SpreadsheetPreview() {
  Spreadsheet()
}

@Composable
fun Spreadsheet(
  sheetViewModel: SpreadsheetViewModel = viewModel()
) {
  val columnNames = sheetViewModel.columnNames
  val rowNumbers = sheetViewModel.rowNumbers

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
      "Row",
      fontSize = fontSize,
      modifier = Modifier.width(rowHeaderWidth)
    )
    for (col in columnNames) {
      Text(
        col,
        fontSize = fontSize,
        textAlign = TextAlign.Center,
        modifier = Modifier.width(columnWidth)
      )
    }
  }
}

@Composable
fun RowData(rowNum: Int, columnNames: List<String>) {
  Row(verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth()) {
    Text(
      rowNum.toString(),
      fontSize = fontSize,
      modifier = Modifier.width(rowHeaderWidth)
    )
    for ( col in columnNames ) {
      Cell(
        key = "${col}${rowNum}",
        modifier = Modifier.width(columnWidth)
      )
    }
  }
}

@Composable
fun Cell(
  key: String,
  modifier: Modifier = Modifier
) {
  val sheetViewModel = viewModel<SpreadsheetViewModel>()
  val value = sheetViewModel.data[key]

  TextField(
    value = (value ?: "").toString(),
    onValueChange = { x: String ->
      sheetViewModel.data[key] = x.toIntOrNull() },
    singleLine = true,
    textStyle = TextStyle(
      fontSize = fontSize, textAlign = TextAlign.End),
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Number
    ),
    modifier = modifier
  )
}

