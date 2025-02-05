package dev.csse.kubiak.demoweek5.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.csse.kubiak.demoweek5.Pie
import kotlinx.serialization.Serializable

sealed class Routes {
  @Serializable
  data object List

  @Serializable
  data class Detail(val pieId: Int )
}

@Composable
fun PieApp(
  modifier: Modifier = Modifier
) {
  val navController = rememberNavController()

  NavHost(
    navController = navController, startDestination = Routes.List
  ) {
    composable<Routes.List> {
      val pieViewModel = viewModel<PieGridViewModel>()

      if (!pieViewModel.hasPies())
        pieViewModel.createSampleData()

      val pies = pieViewModel.getPies()

      Scaffold(
        topBar = {
          PieAppBar("π-terest")
        }
      ) { innerPadding ->
        PieGridScreen(
          pies = pies,
          onSelect = { pie ->
            navController.navigate(Routes.Detail(pie.id))
          },
          modifier = Modifier.padding(innerPadding)
        )
      }
    }

    composable<Routes.Detail> { backStackEntry ->
      val pieViewModel = viewModel<PieDetailViewModel>()
      val detail: Routes.Detail = backStackEntry.toRoute()
      pieViewModel.loadById (detail.pieId)

      val pie = pieViewModel.getCurrent()

      Scaffold(
        topBar = {
          PieAppBar(pie.name,
            canNavigateBack = true,
            onUpClick = { navController.navigateUp() })
        }
      ) { innerPadding ->
        PieDetailScreen(
          pie = pie,
          modifier = Modifier.padding(innerPadding)
        )
      }
    }
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PieAppBar(
  title: String,
  modifier: Modifier = Modifier,
  canNavigateBack: Boolean = false,
  onUpClick: () -> Unit = { },
) {
  TopAppBar(
    title = { Text(title) },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer
    ),
    modifier = modifier,
    navigationIcon = {
      if (canNavigateBack) {
        IconButton(onClick = onUpClick) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
        }
      }
    }
  )
}