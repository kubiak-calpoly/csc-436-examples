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

val fontSize = 24.sp
val columnWidth = 16.dp * 5
val rowHeaderWidth = 16.dp * 3

@Preview
@Composable
fun Spreadsheet() {
  val numRows = 3
  val numCols = 3

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(24.dp)
  ) {
    ColumnHeader(numCols)
    for (row in (1)..numRows) {
      RowData(row, numCols)
    }
  }
}

@Composable
fun ColumnHeader(numCols: Int) {
  Row(verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth() ) {
    Text(
      "Row",
      fontSize = fontSize,
      modifier = Modifier.width(rowHeaderWidth)
    )
    for (col in (1)..numCols) {
      Text(
        Char(col + 'A'.code - 1).toString(),
        fontSize = fontSize,
        textAlign = TextAlign.Center,
        modifier = Modifier.width(columnWidth)
      )
    }
  }
}

@Composable
fun RowData(rowNum: Int, numCols: Int) {
  val initialData = Array<Int?>(numCols) {i -> null}
  var rowData: MutableList<Int?> =
    remember { mutableStateListOf<Int?>(*initialData) }
  Row(verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth()) {
    Text(
      rowNum.toString(),
      fontSize = fontSize,
      modifier = Modifier.width(rowHeaderWidth)
    )
    for ( col in (1)..numCols ) {
      Cell(
        data = rowData[col-1],
        onDataChanged = {x -> rowData[col-1] = x},
        modifier = Modifier.width(columnWidth))
    }
  }
}

@Composable
fun Cell(
  data: Int?,
  onDataChanged: (Int?) -> Unit,
  modifier: Modifier = Modifier
) {
    TextField(
    value = (data ?: "").toString(),
    onValueChange = { x: String -> onDataChanged(x.toIntOrNull()) },
    singleLine = true,
    textStyle = TextStyle(
      fontSize = fontSize, textAlign = TextAlign.End),
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Number
    ),
    modifier = modifier
  )
}

