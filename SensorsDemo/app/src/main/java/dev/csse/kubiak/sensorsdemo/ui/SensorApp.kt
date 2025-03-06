package dev.csse.kubiak.sensorsdemo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReusableContentHost
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import dev.csse.kubiak.sensorsdemo.R


sealed class Routes {
  @Serializable
  data object Inventory

  @Serializable
  data object Compass
}

fun composable(function: Any) {}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorApp(
  modifier: Modifier = Modifier,
  viewModel: SensorViewModel = viewModel(
    factory = SensorViewModel.Companion.Factory
  )
) {
  val context = LocalContext.current
  val navController = rememberNavController()

  Scaffold(topBar = {
    TopAppBar(title = { Text("Sensors Demo") }, actions = {
    })
  }, bottomBar = {
    BottomNavBar(navController)
  },
    modifier = modifier.fillMaxSize()
  ) { innerPadding ->
    NavHost(
      navController = navController,
      startDestination = Routes.Compass
    ) {
      composable<Routes.Inventory> {
        SensorInventoryScreen(modifier = Modifier.padding(innerPadding))
      }

      composable<Routes.Compass> {
        CompassScreen(modifier = Modifier.padding(innerPadding))
      }
    }
  }
}

enum class AppScreen(val route: Any, val title: String, val icon: Int) {
  INVENTORY(
    Routes.Inventory, "List",
    R.drawable.outline_view_list_24
  ),
  COMPASS(
    Routes.Compass, "Compass",
    R.drawable.noun_compass_24
  ),
}

@Composable
fun BottomNavBar(
  navController: NavController,
  modifier: Modifier = Modifier
) {
  val backStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = backStackEntry?.destination?.route


  NavigationBar(modifier = modifier) {
    AppScreen.entries.forEach { item ->
      val selected = currentRoute?.endsWith(item.route.toString()) == true
      NavigationBarItem(
        selected = selected,
        onClick = {
          navController.navigate(item.route)
        },
        icon = {
          Icon(
            painterResource(item.icon),
            contentDescription = item.title
          )
        },
        label = {
          Text(item.title)
        })
    }
  }
}