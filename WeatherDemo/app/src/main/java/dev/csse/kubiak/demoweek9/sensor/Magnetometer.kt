package dev.csse.kubiak.demoweek9.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class Magnetometer(context: Context) : AndroidSensor(
  context = context,
  sensorFeature = PackageManager.FEATURE_SENSOR_COMPASS,
  sensorType = Sensor.TYPE_MAGNETIC_FIELD
)