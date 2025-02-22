package dev.csse.kubiak.demoweek8.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.csse.kubiak.demoweek8.data.AppStorage
import dev.csse.kubiak.demoweek8.AudioEngine
import dev.csse.kubiak.demoweek8.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


sealed class Routes {
  @Serializable
  data object Config

  @Serializable
  data object Play

  @Serializable
  data class Library(
    val shouldSave: Boolean = false
  )

  @Serializable
  data object Sample
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LooperApp(
  modifier: Modifier = Modifier,
  engine: AudioEngine,
  looperViewModel: LooperViewModel = viewModel(
    factory = LooperViewModel.Factory
  ),
  playerViewModel: PlayerViewModel = viewModel(),
) {
  val context = LocalContext.current
  val navController = rememberNavController()
  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("L∞per",
          style = MaterialTheme.typography.displayMedium) },
        actions = {
          TextButton(
            onClick = {
              navController.navigate(
                Routes.Library(shouldSave = true)
              )
            }
          ) {
            Text("Save")
          }
        }
      )
    },
    bottomBar = {
      BottomNavBar(navController)
    }
  ) { innerPadding ->
    val modPadding = Modifier.padding(innerPadding)

    NavHost(
      navController = navController,
      startDestination = Routes.Config
    ) {
      composable<Routes.Config> {
        ConfigScreen(
          engine = engine,
          modifier = modPadding,
          looperViewModel = looperViewModel
        )
      }

      composable<Routes.Play> {
        PlayScreen(
          looperViewModel.loop,
          looperViewModel.tracks,
          modifier = modPadding,
          playerViewModel = playerViewModel,
          bottomBar = {
            LoopPlayer(engine,
              playerViewModel = playerViewModel,
              looperViewModel = looperViewModel
            )
          }
        )
      }

      composable<Routes.Sample> {
        SamplerScreen(
          modifier = modPadding
        )
      }

      composable<Routes.Library> { backStackEntry ->
        val route: Routes.Library =
          backStackEntry.toRoute()
        val store = AppStorage(context)
        LibraryScreen(
          shouldSave = route.shouldSave,
          modifier = modPadding,
          looperViewModel = looperViewModel,
          onLoopLoaded = { loop ->
            coroutineScope.launch(Dispatchers.Default) {
              store.saveLoopBars(loop.barsToLoop)
              store.saveLoopBeats(loop.beatsPerBar)
              store.saveSubdivisions(loop.subdivisions)
            }
            navController.navigate(Routes.Config)
          }
        )
      }
    }

  }
}

enum class AppScreen(val route: Any, val title: String, val icon: Int) {
  LIBRARY(Routes.Library(), "Library", R.drawable.outline_view_list_24),
  GRID(Routes.Play, "Grid", R.drawable.outline_dataset_24),
  SAMPLE(Routes.Sample, "Sampler", R.drawable.outline_mic_none_24 ),
  CONFIG(Routes.Config, "Config", R.drawable.outline_settings_24)
}

@Composable
fun BottomNavBar(navController: NavController) {
  val backStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = backStackEntry?.destination?.route

  NavigationBar {
    AppScreen.entries.forEach { item ->
      NavigationBarItem(
        selected = currentRoute?.endsWith(item.route.toString()) == true,
        onClick = {
          navController.navigate(item.route)
        },
        icon = {
          Icon(painterResource(item.icon), contentDescription = item.title)
        },
        label = {
          Text(item.title)
        }
      )
    }
  }
}