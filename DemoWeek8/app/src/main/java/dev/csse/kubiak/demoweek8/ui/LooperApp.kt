package dev.csse.kubiak.demoweek8.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.csse.kubiak.demoweek8.AudioEngine
import dev.csse.kubiak.demoweek8.ui.ConfigScreen
import dev.csse.kubiak.demoweek8.ui.PlayScreen
import dev.csse.kubiak.demoweek8.Loop
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
  engine: AudioEngine,
  looperViewModel: LooperViewModel = viewModel(),
  playerViewModel: PlayerViewModel = viewModel(),
) {
  val navController = rememberNavController()

  Scaffold(
    bottomBar = {
      AudioPlayer(
        looperViewModel.loop,
        engine = engine,
        playerViewModel = playerViewModel
      )
    }
  ) { innerPadding ->
    val modPadding = Modifier.padding(innerPadding)

    NavHost(
      navController = navController,
      startDestination = Routes.Play
    ) {
      composable<Routes.Config> {
        ConfigScreen(modifier = modPadding)
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