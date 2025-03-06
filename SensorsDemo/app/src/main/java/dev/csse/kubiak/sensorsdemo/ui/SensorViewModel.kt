package dev.csse.kubiak.sensorsdemo.ui

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.csse.kubiak.sensorsdemo.SensorApplication
import dev.csse.kubiak.sensorsdemo.sensor.Accelerometer
import dev.csse.kubiak.sensorsdemo.sensor.Magnetometer

class SensorViewModel(
  val context: Context,
) : ViewModel() {

  var sensorManager: SensorManager
  var accelerometerSensor: Accelerometer
  var magnetometerSensor: Magnetometer

  init {
    sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE)
          as SensorManager

    accelerometerSensor = Accelerometer(context)
    magnetometerSensor = Magnetometer(context)
  }

  fun getSensorList(type: Int = Sensor.TYPE_ALL): List<Sensor> {
    return sensorManager.getSensorList(type)
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application =
          (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as Application)
        SensorViewModel(
          application.applicationContext
        )
      }
    }
  }

  var rotation by mutableFloatStateOf(0.0f)
  private var accelValues = mutableListOf(0.0f, 0.0f, 0.0f)
  private var magneticValues = mutableListOf(0.0f, 0.0f, 0.0f)

  fun startListening() {
    accelerometerSensor.startListening { values ->
      accelValues = values.toMutableList()
      rotation = computeRotation()
    }

    magnetometerSensor.startListening { values ->
      magneticValues = values.toMutableList()
      rotation = computeRotation()
    }
  }

  fun stopListening() {
    accelerometerSensor.stopListening()
    magnetometerSensor.stopListening()
  }

  private fun computeRotation(): Float {
    val rotationMatrix = FloatArray(9)

    // Compute rotation matrix
    if (SensorManager.getRotationMatrix(rotationMatrix, null,
        accelValues.toFloatArray(), magneticValues.toFloatArray())) {

      // Compute orientation values
      val orientation = FloatArray(3)
      SensorManager.getOrientation(rotationMatrix, orientation)

      // Convert azimuth rotation angle from radians to degrees
      val azimuthAngle = Math.toDegrees(orientation[0].toDouble()).toFloat()

      // Rotation is the opposite direction of azimuth
      return -azimuthAngle
    }

    return 0.0f
  }
}