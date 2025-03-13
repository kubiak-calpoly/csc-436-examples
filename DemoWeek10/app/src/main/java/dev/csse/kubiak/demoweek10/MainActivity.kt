package dev.csse.kubiak.demoweek10

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import dev.csse.kubiak.demoweek10.ui.BluetoothApp
import dev.csse.kubiak.demoweek10.ui.BluetoothViewModel
import dev.csse.kubiak.demoweek10.ui.theme.DemoWeek10Theme

class MainActivity : ComponentActivity() {

  private val bluetoothController by lazy {
    BTController(applicationContext)
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
          perms[Manifest.permission.BLUETOOTH_CONNECT] == true
        else true

      if(canEnableBluetooth && !isBluetoothEnabled) {
        enableBluetoothLauncher.launch(
          Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        )
      }
    }

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      permissionLauncher.launch(
        arrayOf(
          Manifest.permission.BLUETOOTH_SCAN,
          Manifest.permission.BLUETOOTH_CONNECT
        )
      )
    }
    setContent {
      DemoWeek10Theme {
        BluetoothApp(
          BluetoothViewModel(bluetoothController)
        )
      }
    }
  }
}
