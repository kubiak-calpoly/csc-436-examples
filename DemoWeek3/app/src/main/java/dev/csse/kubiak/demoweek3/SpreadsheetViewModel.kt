package dev.csse.kubiak.demoweek3

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

val initialColumns = arrayOf("A", "B", "C")
val initialRows = arrayOf(1, 2, 3)

class SpreadsheetViewModel : ViewModel() {
  var columnNames =
    mutableStateListOf<String>(*initialColumns)

  var rowNumbers =
    mutableStateListOf<Int>(*initialRows)

  var data = mutableStateMapOf<String, Int?>()
}

