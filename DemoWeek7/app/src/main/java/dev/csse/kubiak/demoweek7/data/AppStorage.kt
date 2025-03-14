package dev.csse.kubiak.demoweek7.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

data class AppPreferences(
  val loopBars: Int = 1,
  val loopBeats: Int = 4,
  val loopDivisions: Int = 2,
  val playIterations: Int = 8,
  val playSpeed: Int = 120
)

class AppStorage(private val context: Context) {
  companion object {
    private val Context.dataStore by preferencesDataStore("app_storage")

    private object PreferenceKeys {
      val LOOP_BARS = intPreferencesKey("loopBars")
      val LOOP_BEATS = intPreferencesKey("loopBeats")
      val LOOP_DIVISIONS = intPreferencesKey("loopDivisions")
      val PLAY_ITERATIONS = intPreferencesKey("playIterations")
      val PLAY_SPEED = intPreferencesKey("playSpeed")
    }

    init {
      Log.d("AppStorage","Initializing Companion object")
    }
  }

  suspend fun saveLoopBars(barsToLoop: Int) {
    context.dataStore.edit { prefs ->
      prefs[PreferenceKeys.LOOP_BARS] = barsToLoop
    }
  }

  suspend fun saveLoopBeats(beatsPerBar: Int) {
    context.dataStore.edit { prefs ->
      prefs[PreferenceKeys.LOOP_BEATS] = beatsPerBar
    }
  }

  suspend fun saveSubdivisions(subdivisions: Int) {
    context.dataStore.edit { prefs ->
      prefs[PreferenceKeys.LOOP_DIVISIONS] = subdivisions
    }
  }

  suspend fun savePlayerConfig(iterations: Int, bpm: Int) {
    context.dataStore.edit { prefs ->
      prefs[PreferenceKeys.PLAY_ITERATIONS] = iterations
      prefs[PreferenceKeys.PLAY_SPEED] = bpm
    }
  }

  val appPreferencesFlow =
    context.dataStore.data.map { prefs ->
      AppPreferences(
        loopBars = prefs[PreferenceKeys.LOOP_BARS] ?: 1,
        loopBeats = prefs[PreferenceKeys.LOOP_BEATS] ?: 4,
        loopDivisions = prefs[PreferenceKeys.LOOP_DIVISIONS] ?: 2,
        playIterations = prefs[PreferenceKeys.PLAY_ITERATIONS] ?: 8,
        playSpeed = prefs[PreferenceKeys.PLAY_SPEED] ?: 120
      )
    }
}