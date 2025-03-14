package dev.csse.kubiak.sensorsdemo.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

open class AndroidSensor(
  private val context: Context,
  private val sensorFeature: String,
  private val sensorType: Int
) : SensorEventListener {

  private lateinit var sensorManager: SensorManager
  private var sensor: Sensor? = null
  private var onSensorValuesChanged: ((List<Float>) -> Unit)? = null

  val sensorExists: Boolean
    get() = context.packageManager.hasSystemFeature(sensorFeature)

  fun startListening(sensorCallback: (List<Float>) -> Unit) {
    if (sensorExists) {
      if (!::sensorManager.isInitialized && sensor == null) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(sensorType)
      }

      sensor?.let {
        sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        onSensorValuesChanged = sensorCallback
      }
    }
  }

  fun stopListening() {
    if (sensorExists && ::sensorManager.isInitialized) {
      sensorManager.unregisterListener(this)
    }
  }

  override fun onSensorChanged(event: SensorEvent?) {
    if (event?.sensor?.type == (sensor?.type ?: 0)) {
      onSensorValuesChanged?.invoke(event.values.toList())
    }
  }

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}