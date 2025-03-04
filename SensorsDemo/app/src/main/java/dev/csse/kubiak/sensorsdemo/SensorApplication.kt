package dev.csse.kubiak.sensorsdemo

import android.app.Application
import dev.csse.kubiak.sensorsdemo.sensor.Accelerometer
import dev.csse.kubiak.sensorsdemo.sensor.Magnetometer


class SensorApplication : Application() {
  lateinit var accelerometerSensor: Accelerometer
  lateinit var magnetometerSensor: Magnetometer

  override fun onCreate() {
    super.onCreate()

    accelerometerSensor = Accelerometer(this)
    magnetometerSensor = Magnetometer(this)
  }
}