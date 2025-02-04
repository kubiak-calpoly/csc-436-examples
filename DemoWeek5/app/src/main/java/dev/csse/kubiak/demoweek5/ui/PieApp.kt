package dev.csse.kubiak.demoweek5.ui

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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

sealed class Routes {
  @Serializable
  data object List

  @Serializable
  data object Detail
}

@Composable
fun PieApp(
  modifier: Modifier = Modifier, pieViewModel: PieViewModel = viewModel()
) {
  val navController = rememberNavController()

  if (pieViewModel.pieList.isEmpty()) pieViewModel.createSampleData()

  Log.d("PieApp", "${pieViewModel.getPies().size} pies in viewmodel")

  NavHost(
    navController = navController, startDestination = Routes.List
  ) {
    composable<Routes.List> {
      PieGridScreen(
        pies = pieViewModel.getPies(),
        selectedPie = pieViewModel.getCurrent(),
        onPieSelection = { pie ->
          pieViewModel.setCurrent(pie)
          navController.navigate(Routes.Detail)
        }
      )
    }
    composable<Routes.Detail> {
      PieDetailScreen(
        pie = pieViewModel.getCurrent()
      )
    }
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