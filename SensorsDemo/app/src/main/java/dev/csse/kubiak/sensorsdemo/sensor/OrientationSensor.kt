package dev.csse.kubiak.sensorsdemo.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class OrientationSensor(context: Context) : AndroidSensor(
  context = context,
  sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
  sensorType = Sensor.TYPE_GAME_ROTATION_VECTOR
)