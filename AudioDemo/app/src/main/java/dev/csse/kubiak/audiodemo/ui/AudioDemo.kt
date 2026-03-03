package dev.csse.kubiak.audiodemo.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.csse.kubiak.audiodemo.R
import dev.csse.kubiak.audiodemo.audio.AudioEngine
import kotlinx.serialization.Serializable

sealed class Routes {
  @Serializable
  data object Play

  @Serializable
  data object Record

  @Serializable
  data object Sample
}

enum class AppDestinations(
  val route: Any,
  @DrawableRes val icon: Int,
  val title: String
) {
  PLAY(Routes.Play, R.drawable.outline_play_circle_24, "Play"),
  RECORD(Routes.Record, R.drawable.outline_circle_circle_24, "Record"),
  SAMPLE(Routes.Sample, R.drawable.outline_mic_24, "Sample")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioDemo(
  engine: AudioEngine,
  modifier: Modifier = Modifier,
) {
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentDestination = navBackStackEntry?.destination

  Scaffold(
    modifier = modifier,
    topBar = { TopAppBar(
      title = { Text("AudioDemo") }
    )},
    bottomBar = {
      BottomNavBar(navController)
    }
    ) {
    innerPadding ->

    val modPadding = Modifier.padding(innerPadding)

    NavHost(
      navController = navController,
      startDestination = Routes.Play
    ) {
      composable<Routes.Play> {
        PlayerScreen(
          modifier = modPadding
        )
      }
      composable<Routes.Record> {
        RecorderScreen(
          modifier = modPadding
        )
      }
      composable<Routes.Sample> {
        SamplerScreen(
          modifier = modPadding
        )
      }
    }


  }
}


@Composable
fun BottomNavBar(navController: NavController) {
  val backStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = backStackEntry?.destination?.route

  NavigationBar {
    AppDestinations.entries.forEach { item ->
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