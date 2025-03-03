package dev.csse.kubiak.demoweek9.ui


import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
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
import dev.csse.kubiak.demoweek9.R
import kotlinx.serialization.Serializable

const val SLO_COORD_LAT = 35.28275f
const val SLO_COORD_LON = -120.65962f

sealed class Routes {
  @Serializable
  data class Report(
    val lat: Float, val lon: Float
  )

  @Serializable
  data object Somewhere


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(
  viewModel: WeatherViewModel = viewModel(
    factory = WeatherViewModel.Factory
  )
) {
  val context = LocalContext.current
  val navController = rememberNavController()

  Scaffold(topBar = {
    TopAppBar(title = { Text("SLO Sky") }, actions = {
      IconButton(
        enabled = viewModel.isSomewhere(),
        onClick = { viewModel.getWeatherAgain() }
      ) {
        Icon(
          Icons.Filled.Refresh,
          contentDescription = "Refresh",
        )
      }
    })
  }, bottomBar = {
    BottomNavBar(navController)
  },
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    NavHost(
      navController = navController,
      startDestination = Routes.Somewhere
    ) {

      composable<Routes.Report> { backStackEntry ->
        val route: Routes.Report =
          backStackEntry.toRoute()
        WeatherScreen(
          route.lat,
          route.lon,
          modifier = Modifier.padding(innerPadding),
          model = viewModel
        )
      }

      composable<Routes.Somewhere> {
        WeatherScreen(
          SLO_COORD_LAT,
          SLO_COORD_LON,
          modifier = Modifier.padding(innerPadding),
          model =  viewModel
        )
      }
    }
  }
}

enum class AppScreen(val route: Any, val title: String, val icon: Int) {
  HOME(
    Routes.Report(35.640556f, -120.680008f),
    "Home", R.drawable.outline_home_24
  ),
  WORK(
    Routes.Report(35.28275f, -120.65962f),
    "Work", R.drawable.outline_work_24
  ),
  BEACH(
    Routes.Report(35.190349f, -120.731793f), "Beach",
    R.drawable.baseline_beach_access_24
  ),
  PIE(
    Routes.Report(35.489596f,-120.668665f), "Pie",
    R.drawable.outline_pie_24
  ),
}

@Composable
fun BottomNavBar(
  navController: NavController
) {
  val backStackEntry by navController
    .currentBackStackEntryAsState()
  val currentRoute = backStackEntry?.destination?.route


  NavigationBar {
    AppScreen.entries.forEach { item ->
      val selected = (item.route is Routes.Report &&
        currentRoute?.endsWith("Routes.Report/{lat}/{lon}") == true &&
        backStackEntry?.toRoute<Routes.Report>() == item.route )
              || currentRoute?.endsWith(item.route.toString()) == true
      NavigationBarItem(
        selected = selected,
        onClick = {
          navController.navigate(item.route)
        },
        icon = {
          Icon(painterResource(item.icon), contentDescription = item.title)
        },
        label = {
          Text(item.title)
        })
    }
  }
}
