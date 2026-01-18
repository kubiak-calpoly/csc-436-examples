package dev.csse.kubiak.tinyss

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class TinySSViewModel : ViewModel() {

  val columnNames = mutableStateListOf("A", "B", "C")
  val rowNumbers = mutableStateListOf(1, 2, 3)

  private val data = mutableStateMapOf<String, Int?>()

  fun getData(col: String, row: Int)
    = data["$col$row"]

  fun setData(col: String, row: Int, value: Int?) {
    data["$col$row"] = value
  }
}

