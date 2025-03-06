package dev.csse.kubiak.sensorsdemo.ui

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.csse.kubiak.sensorsdemo.SensorApplication
import dev.csse.kubiak.sensorsdemo.sensor.Accelerometer
import dev.csse.kubiak.sensorsdemo.sensor.Gyroscope
import dev.csse.kubiak.sensorsdemo.sensor.Magnetometer

class SensorViewModel(
  val context: Context,
) : ViewModel() {

  private var sensorManager: SensorManager
  private var gyroscope: Gyroscope
  private var accelerometer: Accelerometer
  private var magnetometer: Magnetometer

  var gyroValues by mutableStateOf( listOf(0.0f, 0.0f, 0.0f))
  var accelValues by mutableStateOf( listOf(0.0f, 0.0f, 0.0f))
  var magneticValues by mutableStateOf( listOf(0.0f, 0.0f, 0.0f))
  var compassRotation by mutableFloatStateOf(0.0f)

  init {
    sensorManager =
      context.getSystemService(Context.SENSOR_SERVICE)
              as SensorManager

    accelerometer = Accelerometer(context)
    magnetometer = Magnetometer(context)
    gyroscope = Gyroscope(context)
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

  fun startGyro() {
    gyroscope.startListening { values ->
      gyroValues = values.toMutableList()
      Log.i("SensorViewModel", "Gyro event ${gyroValues}")
    }
  }

  fun stopGyro() {
    gyroscope.stopListening()
  }

  fun startAccel() {
    accelerometer.startListening { values ->
      accelValues = values.toMutableList()
      Log.i("SensorViewModel", "Accel event ${accelValues}")
    }
  }

  fun stopAccel() {
    accelerometer .stopListening()
  }

  fun startCompass() {
    accelerometer.startListening { values ->
      accelValues = values
      compassRotation = computeRotation()
    }

    magnetometer.startListening { values ->
      magneticValues = values
      compassRotation = computeRotation()
    }
  }

  fun stopCompass() {
    accelerometer.stopListening()
    magnetometer.stopListening()
  }

  private fun computeRotation(): Float {
    val rotationMatrix = FloatArray(9)

    // Compute rotation matrix
    if (SensorManager.getRotationMatrix(
        rotationMatrix, null,
        accelValues.toFloatArray(), magneticValues.toFloatArray()
      )
    ) {

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