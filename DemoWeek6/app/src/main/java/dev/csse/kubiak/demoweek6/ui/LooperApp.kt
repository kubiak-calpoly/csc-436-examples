package dev.csse.kubiak.demoweek6.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.csse.kubiak.demoweek6.Loop
import kotlinx.serialization.Serializable


sealed class Routes {
  @Serializable
  data object Config

  @Serializable
  data object Play
}

@Composable
fun LooperApp(
  modifier: Modifier = Modifier,
  looperViewModel: LooperViewModel = viewModel()
) {
  val navController = rememberNavController()

  Scaffold() { innerPadding ->
    val modPadding = Modifier.padding(innerPadding)

    NavHost(
      navController = navController,
      startDestination = Routes.Play
    ) {
      composable<Routes.Config> {
        ConfigScreen(modifier = modPadding)
      }

      composable<Routes.Play> {
        PlayScreen(looperViewModel.loop, modifier = modPadding)
      }
    }

  }
}