package dev.csse.kubiak.btdemo

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import dev.csse.kubiak.btdemo.bt.BTController
import dev.csse.kubiak.btdemo.ui.BluetoothApp
import dev.csse.kubiak.btdemo.ui.BluetoothViewModel
import dev.csse.kubiak.btdemo.ui.theme.BTDemoTheme

class MainActivity : ComponentActivity() {

  private val bluetoothController by lazy {
    BTController(this)
  }

  private val isBluetoothEnabled: Boolean
    get() = bluetoothController.isBluetoothEnabled

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val viewModel = BluetoothViewModel(bluetoothController)

    val enableBluetoothLauncher = registerForActivityResult(
      ActivityResultContracts.StartActivityForResult()
    ) { /* Not needed */ }

    val permissionLauncher = registerForActivityResult(
      ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
      val canEnableBluetooth =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
          perms[Manifest.permission.BLUETOOTH_CONNECT] == true
        else true

      if (canEnableBluetooth && !isBluetoothEnabled) {
        enableBluetoothLauncher.launch(
          Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        )
      }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      permissionLauncher.launch(
        arrayOf(
          Manifest.permission.BLUETOOTH_SCAN,
          Manifest.permission.BLUETOOTH_CONNECT
        )
      )
    } else {
      permissionLauncher.launch(
        arrayOf(
          Manifest.permission.BLUETOOTH_ADMIN,
          Manifest.permission.BLUETOOTH,
          Manifest.permission.ACCESS_FINE_LOCATION
        )
      )
    }

    setContent {
      BTDemoTheme {
        val state by viewModel.state.collectAsState()

        LaunchedEffect(key1 = state.errorMessage) {
          state.errorMessage?.let { message ->
            Toast.makeText(
              applicationContext,
              message,
              Toast.LENGTH_LONG
            ).show()
          }
        }

        LaunchedEffect(key1 = state.isConnected) {
          if(state.isConnected) {
            Toast.makeText(
              applicationContext,
              "You're connected!",
              Toast.LENGTH_LONG
            ).show()
          }
        }

        BluetoothApp(viewModel)
      }
    }
  }
}
