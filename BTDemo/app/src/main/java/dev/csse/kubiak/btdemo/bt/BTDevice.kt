package dev.csse.kubiak.btdemo.bt

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice

@SuppressLint("MissingPermission")
class BTDevice(device: BluetoothDevice) {
  val name: String? = device.name
  val address: String = device.address
  val type: Int = device.type
}
