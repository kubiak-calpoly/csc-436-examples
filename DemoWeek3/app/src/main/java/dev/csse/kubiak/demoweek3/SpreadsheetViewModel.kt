package dev.csse.kubiak.demoweek3

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class SpreadsheetViewModel : ViewModel() {

  val columnNames = mutableStateListOf("A", "B", "C")
  val rowNumbers = mutableStateListOf(1, 2, 3)

  val data = mutableStateMapOf<String, Int?>()
}

