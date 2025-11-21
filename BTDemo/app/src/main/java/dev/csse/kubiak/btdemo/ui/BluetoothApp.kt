package dev.csse.kubiak.btdemo.ui

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.csse.kubiak.btdemo.R
import kotlinx.serialization.Serializable

sealed class Routes {
  @Serializable
  data object Discover

  @Serializable
  data object Chat
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothApp(
  model: BluetoothViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val navController = rememberNavController()

  Scaffold(
    topBar = {
      TopAppBar(title = { Text("Bluetooth Demo") })
    }, bottomBar = {
      BottomNavBar(navController)
    },
    modifier = modifier.fillMaxSize()
  ) { innerPadding ->
    NavHost(
      navController = navController,
      startDestination = Routes.Discover
    ) {
      composable<Routes.Discover> {
        DiscoveryScreen(
          model,
          onStartScan = {model.startDiscovery()},
          onStopScan = {model.stopDiscovery()},
          onClearScan = {model.clearDiscovery()},
          onClickDevice = { model.connectToDevice(it)},
          onStartServer = { model.waitForIncomingConnections() },
          modifier = modifier.padding(innerPadding)
        )
      }
      composable<Routes.Chat> {
        ChatScreen(
          model,
          modifier = modifier.padding(innerPadding),
          onDisconnect =  { model.disconnectFromDevice() },
          onSendMessage =  { model.sendMessage(it)}
        )
      }
    }
  }
}

enum class AppScreen(
  val route: Any,
  val title: String, val icon: Int
) {
  INVENTORY(
    Routes.Discover, "Discover",
    R.drawable.baseline_bluetooth_searching_24
  ),
  CHAT(
    Routes.Chat, "Chat",
    R.drawable.outline_chat_24
  )
}

@Composable
fun BottomNavBar(
  navController: NavHostController,
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
