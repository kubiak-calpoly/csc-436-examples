package dev.csse.kubiak.demoweek3

import android.content.res.Configuration
import android.nfc.Tag
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.csse.kubiak.demoweek3.ui.theme.DemoWeek3Theme

private const val TAG = "LifeCycleDemo"

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    Log.i(TAG, "onCreate $savedInstanceState")
    setContent {
      var name: String by rememberSaveable { mutableStateOf("Android") }

      Column( modifier = Modifier.padding(24.dp) ) {
        Greeting( name = name )
        TextField( value = name,
          onValueChange =  { x: String -> name = x },
          label = { Text("What is your name?") }
        )
      }
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    Log.i(TAG, "onSaveInstanceState")
  }

  override fun onStart() {
    super.onStart()
    Log.i(TAG, "onStart")
  }

  override fun onResume() {
    super.onResume()
    Log.i(TAG, "onResume")
  }

  override fun onPause() {
    super.onPause()
    Log.i(TAG, "onPause")
  }

  override fun onStop() {
    super.onStop()
    Log.i(TAG, "onStop")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.i(TAG, "onDestroy")
  }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}
