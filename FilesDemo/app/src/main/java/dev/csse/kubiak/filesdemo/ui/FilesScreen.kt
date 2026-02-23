package dev.csse.kubiak.filesdemo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FilesScreen(
  modifier: Modifier = Modifier,
  model: FilesViewModel = viewModel()
) {
  val context = LocalContext.current

  Column(modifier = modifier) {
    TextField(
      model.inputFileName,
      onValueChange = { model.inputFileName = it },
      label = { Text("Input File") }
    )
    Button(
      onClick = {
        model.readInputFile(context)
      }
    ) {
      Text("Open and read the File")
    }
    Card() {
      model.contents.forEach { s ->
        Text(s)
      }
    }
    TextField(
      model.outputFileName,
      onValueChange = { model.outputFileName = it },
      label = { Text("Output File") }
    )
    Button(
      onClick = {
        model.writeOutputFile(context)
      }
    ) {
      Text("Open and write the File")
    }
  }

}