package dev.csse.kubiak.demoweek2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.kubiak.demoweek2.ui.theme.DemoWeek2Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

  }
}

@Composable
fun ColumnDemo(align: Alignment.Horizontal) {
  Column(modifier = Modifier.width(300.dp),
    horizontalAlignment = align
  ) {
    Text("Jabberwock")
    Text("Bandersnatch")
    Text("Jubjub")
    Text("Tumtum")
  }
}

@Preview
@Composable
fun ColumnPreview() {
  ColumnDemo(Alignment.Start)
}


@Composable
fun Greeting(name: String) {
  Column(modifier = Modifier
    // .size(100.dp, 80.dp)
    .alpha(0.25f)
    .background(Color.Cyan)
    .border(5.dp, Color.Red)
    .padding(5.dp, 20.dp, 10.dp, 5.dp)
  ) {
    Text(text = "Hello,")
    Text(text = name)
  }
}

@Preview
@Composable
fun GreetingPreview() {
  Greeting("Layouts")
}

@Preview
@Composable
fun ModOrderPreview() {
  Text(
    text = "Poppy",
    modifier = Modifier.background(Color.Yellow)
      .border(2.dp, Color.Red)
      .padding(5.dp, 3.dp, 10.dp, 2.dp)
  )
}
