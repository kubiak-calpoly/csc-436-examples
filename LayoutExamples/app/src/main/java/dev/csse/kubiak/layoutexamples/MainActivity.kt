package dev.csse.kubiak.layoutexamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.kubiak.layoutexamples.ui.theme.LayoutExamplesTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      LayoutExamplesTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Greeting(
            name = "Android",
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Row(modifier = Modifier
    .size(200.dp, 120.dp)
    .border(5.dp, Color.Blue)
    .padding(5.dp),
    verticalAlignment = Alignment.Top,
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    Text(text = "Hi",
      fontSize = 40.sp,
      modifier = Modifier
        .alignByBaseline()
        .weight(1f)
        .background(Color.Red))
    Text(text = name,
      modifier = Modifier
        .alignByBaseline()
        .weight(2f)
       .background(Color.Red)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  LayoutExamplesTheme {
    Greeting("Android")
  }
}

@Preview(showBackground = true)
@Composable
fun RowPreview() {
  LayoutExamplesTheme {
    Row(modifier = Modifier.width(200.dp)) {
      Text("Hello",
        textAlign = TextAlign.Center,
        modifier = Modifier.background(Color.Yellow)
          .weight(1f)
        )
      Text("Goodbye",
        color = Color.Yellow,
        textAlign = TextAlign.Center,
        modifier = Modifier.background(Color.Gray)
          .weight(2f)
      )
    }
  }
}


@Preview(showBackground = true)
@Composable
fun PaddingPreview() {
  LayoutExamplesTheme {
    Text(
      "Poppy",
      modifier = Modifier
        .background(Color.Yellow)
        .border(10.dp, Color.Blue)
        .padding(15.dp, 20.dp, 10.dp, 20.dp)
        .border(5.dp, Color.Red)
        .padding(5.dp)
    )
  }
}


@Preview(showBackground = true)
@Composable
fun BoxPreview() {

  LayoutExamplesTheme {

    Box(
      modifier = Modifier
        .size(80.dp, 75.dp)
    ) {

      Text(
        "The day of the android has dawned.",
        modifier = Modifier
          .width(60.dp)
          .align(Alignment.TopStart),
        style = MaterialTheme.typography.bodySmall,
      )
      Text(
        "Brian Aldiss",
        modifier = Modifier
          .align(Alignment.BottomEnd),
        color = Color.Gray,
        style = MaterialTheme.typography.bodyMedium
      )
      Image(
        painterResource(R.drawable.android),
        contentDescription = "Android",
        modifier = Modifier
          .width(40.dp)
          .align(Alignment.BottomEnd)
          .padding(bottom = 10.dp)
      )
    }
  }
}