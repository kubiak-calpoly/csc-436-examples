package dev.csse.kubiak.filesdemo.ui

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.PrintWriter

class FilesViewModel: ViewModel() {

  var inputFileName: String by mutableStateOf("")
  var outputFileName: String by mutableStateOf("")
  var contents: MutableList<String> = mutableStateListOf()

  fun readInputFile(context: Context) {
    val inputStream = context.openFileInput(inputFileName)
    val reader = inputStream.bufferedReader()

    contents.clear()

    reader.forEachLine { line ->
      contents.add(line)
    }

    reader.close()
  }

  fun writeOutputFile(context: Context) {
    val outputStream = context
      .openFileOutput(outputFileName, Context.MODE_APPEND)
    val writer = PrintWriter(outputStream)

    contents.forEach { line ->
      writer.println(line)
    }

    writer.close()
  }

  }