package dev.csse.kubiak.demoweek8

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.csse.kubiak.demoweek8.ui.theme.DemoWeek8Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      DemoWeek8Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          AudioApp(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioApp(modifier: Modifier = Modifier) {

  // Fetching the local context
  val mContext = LocalContext.current

  // Declaring and Initializing
  // the MediaPlayer to play a WAV file
  val mMediaPlayer = MediaPlayer.create(mContext, R.raw.rd_t_ft_1)

  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Row {
      // IconButton for Start Action
      IconButton(onClick = { mMediaPlayer.start() }) {
        Icon(
          painter = painterResource(id = R.drawable.baseline_play_circle_outline_24),
          contentDescription = "",
          Modifier.size(100.dp)
        )
      }

      // IconButton for Pause Action
      IconButton(onClick = { mMediaPlayer.pause() }) {
        Icon(
          painter = painterResource(id = R.drawable.baseline_pause_circle_outline_24),
          contentDescription = "",
          Modifier.size(100.dp)
        )
      }
    }
  }


}

// For displaying preview in
// the Android Studio IDE emulator
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  AudioApp()
}