package dev.csse.kubiak.demoweek7.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import dev.csse.kubiak.demoweek7.R


sealed class Routes {
  @Serializable
  data object Config

  @Serializable
  data object Play
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LooperApp(
  modifier: Modifier = Modifier,
  looperViewModel: LooperViewModel = viewModel<LooperViewModel>(),
  playerViewModel: PlayerViewModel = viewModel(),
) {
  val navController = rememberNavController()

  Scaffold(topBar = {
    TopAppBar(
      title = { Text("L∞per",
        style = MaterialTheme.typography.displayMedium) },
      actions = {
        TextButton(
          onClick = {navController.navigate(Routes.Config)}
        ) {
          Icon(
            painterResource(R.drawable.outline_settings_24 ),
            contentDescription = "Config",
            modifier = Modifier.scale(2.0f)
          )
        }
        TextButton(
          onClick = {navController.navigate(Routes.Play)},
        ) {
          Icon(
            painterResource(R.drawable.outline_dataset_24 ),
            contentDescription = "Grid",
            modifier = Modifier.scale(2.0f)
          )
        }
      })
  }
    ) { innerPadding ->
    val modPadding = Modifier.padding(innerPadding)

    NavHost(
      navController = navController,
      startDestination = Routes.Config
    ) {
      composable<Routes.Config> {
        ConfigScreen(modifier = modPadding,
          looperViewModel = looperViewModel
        )
      }

      composable<Routes.Play> {
        PlayScreen(
          looperViewModel.loop,
          modifier = modPadding,
          playerViewModel = playerViewModel
        )
      }
    }

  }
}