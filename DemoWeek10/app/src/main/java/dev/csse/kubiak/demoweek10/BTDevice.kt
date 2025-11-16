package dev.csse.kubiak.demoweek10

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.os.Build
import androidx.annotation.RequiresPermission

@SuppressLint("MissingPermission")
class BTDevice(device: BluetoothDevice) {
  val name: String? = device.name
  val address: String = device.address
  val type: Int = device.type
}
