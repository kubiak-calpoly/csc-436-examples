package dev.csse.kubiak.demoweek10.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek10.BTDevice
import org.w3c.dom.Text

@Composable
fun DiscoveryScreen(
  model: BluetoothViewModel,
  modifier: Modifier = Modifier,
  onStartScan: () -> Unit = {},
  onStopScan: () -> Unit = {},
  onClearScan: () -> Unit = {},
  onClickDevice: (BTDevice) -> Unit = {},
  onStartServer: () -> Unit = {}
) {
  val state: BluetoothUiState by
  model.state.collectAsStateWithLifecycle()



  Column(modifier = modifier.fillMaxSize()) {

    LazyColumn(
      modifier = Modifier.weight(1f)
    ) {

      item {
        Text(
          text = "Paired Devices",
          fontWeight = FontWeight.Bold,
          fontSize = 24.sp,
          modifier = Modifier.padding(16.dp)
        )
      }

      items(state.pairedDevices) { device ->
        if (device.name != null) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(device.name)
            Button(onClick = { onClickDevice(device) }) {
              Text("Connect")
            }
          }
        }
      }

      item {
        Text(
          text = "Scanned Devices",
          fontWeight = FontWeight.Bold,
          fontSize = 24.sp,
          modifier = Modifier.padding(16.dp)
        )
      }

      items(state.scannedDevices) { device ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(device.name ?: device.address)
          if (device.name != null) {
            Button(onClick = { onClickDevice(device) }) {
              Text("Connect")
            }
          }
        }
      }
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          Button(onClick = onStartScan) {
            Text("Start Scan")
          }
          Button(onClick = onStopScan) {
            Text("Stop Scan")
          }
          Button(onClick = onClearScan) {
            Text("Clear")
          }
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          Button(onClick = onStartServer) {
            Text("Start Server")
          }
        }
      }
    }
  }
}
