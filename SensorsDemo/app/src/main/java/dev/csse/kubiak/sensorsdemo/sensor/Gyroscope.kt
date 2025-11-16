package dev.csse.kubiak.sensorsdemo.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class Gyroscope(context: Context) : AndroidSensor(
  context = context,
  sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE,
  sensorType = Sensor.TYPE_GYROSCOPE
)