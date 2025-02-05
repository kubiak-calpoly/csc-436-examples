package dev.csse.kubiak.demoweek5.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Composable
fun PieApp(
  pieViewModel: PieViewModel = viewModel()
) {
  if (pieViewModel.pieList.isEmpty()) pieViewModel.createSampleData()

  Log.d("PieApp", "${pieViewModel.getPies().size} pies in viewmodel")

  Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

    PieGridScreen(
      pies = pieViewModel.getPies(),
      selectedPie = pieViewModel.getCurrent(),
      onPieSelection = { pie ->
        pieViewModel.setCurrent(pie)
      },
      modifier = Modifier.padding(innerPadding)
    )
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PieAppBar(
  title: String, modifier: Modifier = Modifier
) {
  TopAppBar(
    title = { Text(title) }, colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer
    ), modifier = modifier
  )
}