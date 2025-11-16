package dev.csse.kubiak.demoweek10

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

sealed class ConnectionResult {
  object ConnectionEstablished: ConnectionResult()
  data class TransferSucceeded(
    val message: BTMessage
  ): ConnectionResult()
  data class Error(val message: String): ConnectionResult()
}

class BTController(
  val activity: Activity
) {
  val context = activity as Context

  private val manager =
    context.getSystemService(Context.BLUETOOTH_SERVICE)
            as BluetoothManager

  private val adapter = manager.adapter

  private val _isConnected = MutableStateFlow(false)
  val isConnected: StateFlow<Boolean>
    get() = _isConnected.asStateFlow()

  private val _scannedDevices = MutableStateFlow<List<BTDevice>>(emptyList())
  val scannedDevices: StateFlow<List<BTDevice>>
    get() = _scannedDevices.asStateFlow()

  private val _pairedDevices = MutableStateFlow<List<BTDevice>>(emptyList())
  val pairedDevices: StateFlow<List<BTDevice>>
    get() = _pairedDevices.asStateFlow()

  private val _errors = MutableSharedFlow<String>()
  val errors: SharedFlow<String>
    get() = _errors.asSharedFlow()

  @SuppressLint("MissingPermission")
  private val foundDeviceReceiver = FoundDeviceReceiver { device ->
    Log.d("BTController", "found device ${device}")
    _scannedDevices.update { devices ->
      val newDevice = BTDevice(device)
      if (newDevice in devices) devices else devices + newDevice
    }
  }

  @SuppressLint("MissingPermission")
  private val bluetoothStateReceiver = BTStateReceiver { isConnected, bluetoothDevice ->
    if(adapter?.bondedDevices?.contains(bluetoothDevice) == true) {
      _isConnected.update { isConnected }
    } else {
      CoroutineScope(Dispatchers.IO).launch {
        _errors.emit("Can't connect to a non-paired device.")
      }
    }
  }

  private val discoverableIntent: Intent =
    Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
      putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
    }

  private var currentServerSocket: BluetoothServerSocket? = null
  private var currentClientSocket: BluetoothSocket? = null

  private var dataTransferService: BTDataTransferService? = null

  init {
    updatePairedDevices()
    context.registerReceiver(
      bluetoothStateReceiver,
      IntentFilter().apply {
        addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
        addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
      }
    )
  }

  val isBluetoothEnabled: Boolean
    get() = adapter.isEnabled == true

  @SuppressLint("MissingPermission")
  fun startDiscovery() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
      !hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
      Log.e("BTController", "no permission to scan")
      return
    }

    Log.i("BTController", "scanning")

    context.registerReceiver(
      foundDeviceReceiver,
      IntentFilter(BluetoothDevice.ACTION_FOUND)
    )

    updatePairedDevices()

    adapter.startDiscovery()
  }

  @SuppressLint("MissingPermission")
  fun stopDiscovery() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
      !hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
      return
    }

    adapter.cancelDiscovery()
  }

  fun clearDiscovery() {
    _scannedDevices.update { listOf<BTDevice>() }
    _pairedDevices.update { listOf<BTDevice>() }
  }

  fun closeConnection() {
    currentClientSocket?.close()
    currentServerSocket?.close()
    currentClientSocket = null
    currentServerSocket = null
  }

  fun release() {
    context.unregisterReceiver(foundDeviceReceiver)
    context.unregisterReceiver(bluetoothStateReceiver)
    closeConnection()
  }

  @SuppressLint("MissingPermission")
  private fun updatePairedDevices() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
      !hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
      Log.e("BTController", "no permission to connect")
      return
    }
    Log.d("BTController", "updating paired Devices")
    adapter
      .bondedDevices
      .map { BTDevice(it) }
      .also { devices ->
        _pairedDevices.update { devices }
      }
  }

  private fun hasPermission(permission: String): Boolean {
    return context.checkSelfPermission(permission) ==
            PackageManager.PERMISSION_GRANTED
  }

  @SuppressLint("MissingPermission")
  fun startBluetoothServer(): Flow<ConnectionResult> {
    return flow {
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
        !hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
        throw SecurityException("No BLUETOOTH_CONNECT permission")
      }

      currentServerSocket = adapter
        ?.listenUsingRfcommWithServiceRecord(
        "chat_service",
        UUID.fromString(SERVICE_UUID)
      )
      Log.d("BTController", "Server started, socket=${currentServerSocket}")

      startActivityForResult(activity, discoverableIntent, 1, null)

      var shouldLoop = true
      while(shouldLoop) {
        currentClientSocket = try {
          currentServerSocket?.accept()
        } catch(e: IOException) {
          Log.e("BTController", "Error: ${e}")
          shouldLoop = false
          null
        }
        Log.i("BTController", "Connected client=${currentClientSocket}")
        emit(ConnectionResult.ConnectionEstablished)
        currentClientSocket?.let {
          Log.d("BTController", "Closing ${currentServerSocket}")
          currentServerSocket?.close()
          val service = BTDataTransferService(it)
          dataTransferService = service

          emitAll(
            service
              .listenForIncomingMessages()
              .map {
                Log.d("BTController", "Read: $it")
                ConnectionResult.TransferSucceeded(it)
              }
          )
        }
      }
    }.onCompletion {
      closeConnection()
    }.flowOn(Dispatchers.IO)
  }

  @SuppressLint("MissingPermission")
  fun connectToDevice(device: BTDevice): Flow<ConnectionResult> {
    Log.i("BTController", "Connecting to device ${device.name ?: device.address}")
    return flow {
      if(!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
        throw SecurityException("No BLUETOOTH_CONNECT permission")
      }

      currentClientSocket = adapter
        ?.getRemoteDevice(device.address)
        ?.createRfcommSocketToServiceRecord(
          UUID.fromString(SERVICE_UUID)
        )

      stopDiscovery()

      currentClientSocket?.let { socket ->
        try {
          socket.connect()
          Log.i("BTController", "Connnection established on ${socket}")
          emit(ConnectionResult.ConnectionEstablished)

          BTDataTransferService(socket).also {
            dataTransferService = it
            emitAll(
              it.listenForIncomingMessages()
                .map { ConnectionResult.TransferSucceeded(it) }
            )
          }
        } catch(e: IOException) {
          socket.close()
          currentClientSocket = null
          emit(ConnectionResult.Error("Connection was interrupted: ${e}"))
        }
      }
    }.onCompletion {
      closeConnection()
    }.flowOn(Dispatchers.IO)
  }

  @SuppressLint("MissingPermission")
  suspend fun trySendMessage(message: String): BTMessage? {
    if(dataTransferService == null) {
      return null
    }

    val bluetoothMessage = BTMessage(
      message = message,
      senderName = adapter?.name ?: "Unknown name",
      isFromLocalUser = true
    )

    dataTransferService?.sendMessage(bluetoothMessage.toByteArray())

    return bluetoothMessage
  }

  companion object {
    const val SERVICE_UUID = "3962a9f9-1dc0-478f-8cae-3cd8699dcedd"
  }
}
