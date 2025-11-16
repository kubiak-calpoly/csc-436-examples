package dev.csse.kubiak.demoweek10.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.csse.kubiak.demoweek10.BTController
import dev.csse.kubiak.demoweek10.ConnectionResult
import dev.csse.kubiak.demoweek10.BTDevice
import dev.csse.kubiak.demoweek10.BTMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class BluetoothUiState(
  val scannedDevices: List<BTDevice> = emptyList(),
  val pairedDevices: List<BTDevice> = emptyList(),
  val isConnected: Boolean = false,
  val isConnecting: Boolean = false,
  val errorMessage: String? = null,
  val messages: List<BTMessage> = emptyList()
)

class BluetoothViewModel(
  private val controller: BTController
) : ViewModel() {
  private val _state = MutableStateFlow(BluetoothUiState())
  val state = combine(
    controller.scannedDevices,
    controller.pairedDevices,
    _state
  ) { scannedDevices, pairedDevices, state ->
    state.copy(
      scannedDevices = scannedDevices,
      pairedDevices = pairedDevices,
      messages = if(state.isConnected) state.messages else emptyList()
    )
  }.stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000),
    _state.value
  )

  private var deviceConnectionJob: Job? = null

  init {
    controller.isConnected.onEach { isConnected ->
      _state.update { it.copy(isConnected = isConnected) }
    }.launchIn(viewModelScope)

    controller.errors.onEach { error ->
      _state.update { it.copy(
        errorMessage = error
      ) }
    }.launchIn(viewModelScope)
  }

  fun startDiscovery() {
    controller.startDiscovery()
  }

  fun stopDiscovery() {
    controller.stopDiscovery()
  }

  fun clearDiscovery() {
    controller.clearDiscovery()
  }

  fun waitForIncomingConnections() {
    _state.update { it.copy(isConnecting = true) }
    deviceConnectionJob = listenForConnections(
      controller.startBluetoothServer()
    )
  }

  fun connectToDevice(device: BTDevice) {
    _state.update { it.copy(isConnecting = true) }
    deviceConnectionJob = listenForConnections(
      controller.connectToDevice(device)
    )
  }

  fun sendMessage(message: String) {
    Log.d("BTViewModel","Sending: ${message}")
    viewModelScope.launch {
      val bluetoothMessage = controller.trySendMessage(message)
      if(bluetoothMessage != null) {
        _state.update { it.copy(
          messages = it.messages + bluetoothMessage
        ) }
      }
    }
  }

  fun disconnectFromDevice() {
    deviceConnectionJob?.cancel()
    controller.closeConnection()
    _state.update { it.copy(
      isConnecting = false,
      isConnected = false
    ) }
  }

  private fun listenForConnections(
    flow: Flow<ConnectionResult>
  ) : Job {
    return flow.onEach {result ->
        when(result) {
          ConnectionResult.ConnectionEstablished -> {
            _state.update { it.copy(
              isConnected = true,
              isConnecting = false,
              errorMessage = null
            ) }
          }
          is ConnectionResult.TransferSucceeded -> {
            Log.d("BTViewModel", "Transfer Succeeded: $result")
            _state.update { it.copy(
              messages = it.messages + result.message
            ) }
          }
          is ConnectionResult.Error -> {
            _state.update { it.copy(
              isConnected = false,
              isConnecting = false,
              errorMessage = result.message
            ) }
          }
        }}
      .catch { throwable ->
        controller.closeConnection()
        _state.update { it.copy(
          isConnected = false,
          isConnecting = false,
        ) } }
      .launchIn(viewModelScope)
  }
}