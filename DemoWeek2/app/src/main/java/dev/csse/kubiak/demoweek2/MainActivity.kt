package dev.csse.kubiak.demoweek2

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.kubiak.demoweek2.ui.theme.DemoWeek2Theme
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

  }
}

@Composable
fun ColumnDemo(
  align: Alignment.Horizontal, arrange: Arrangement.Vertical = Arrangement.Top
) {
  Column(
    modifier = Modifier.size(150.dp), horizontalAlignment = align, verticalArrangement = arrange
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
  ColumnDemo(
    Alignment.End, Arrangement.SpaceAround
  )
}

@Composable
fun Square(color: Color, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .size(20.dp)
      .background(color)
  )
}

@Preview
@Composable
fun BoxPreview() {
  Box(modifier = Modifier.width(150.dp)) {
    Square(Color.Red, Modifier.align(Alignment.TopStart))
    Text(
      """’Twas brillig, and the slithy toves
      Did gyre and gimble in the wabe:
All mimsy were the borogoves,
      And the mome raths outgrabe.
    """.trimIndent()
    )
  }
}

@Composable
fun Greeting(name: String) {
  Column(
    modifier = Modifier
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
    modifier = Modifier
      .background(Color.Yellow)
      .border(2.dp, Color.Red)
      .padding(5.dp, 3.dp, 10.dp, 2.dp)
  )
}

@Composable
fun BankCardUi() {
  // Bank Card Aspect Ratio
  val bankCardAspectRatio = 1.586f // (e.g., width:height = 85.60mm:53.98mm)
  val baseColor = Color(0xFF1252c8)
  Card(
    modifier = Modifier
      .fillMaxWidth()
      // Aspect Ratio in Compose
      .aspectRatio(bankCardAspectRatio),
    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
  ) {
    Box {
      BankCardBackground(baseColor = Color(0xFF1252c8))
      SpaceWrapper(
        modifier = Modifier.align(Alignment.TopStart),
        space = 32.dp,
        top = true,
        left = true
      ) {
        BankCardLabelAndText(label = "card holder", text = "John Doe")
      }
      BankCardNumber("1234567890123456")
      SpaceWrapper(
        modifier = Modifier.align(Alignment.BottomStart),
        space = 32.dp,
        bottom = true,
        left = true
      ) {
        Row {
          BankCardLabelAndText(label = "expires", text = "01/29")
          Spacer(modifier = Modifier.width(16.dp))
          BankCardLabelAndText(label = "cvv", text = "901")
        }
      }
      SpaceWrapper(
        modifier = Modifier.align(Alignment.BottomEnd),
        space = 32.dp,
        bottom = true,
        right = true
      ) {
        // Feel free to use an image instead
        Text(
          text = "WISA",
          fontWeight = FontWeight.W500,
          fontStyle = FontStyle.Italic,
          fontSize = 22.sp,
          letterSpacing = 1.sp,
          color = Color.White
        )
      }
    }
  }
}

// A splash of color for the background
@Composable
fun BankCardBackground(baseColor: Color) {
  Canvas(
    modifier = Modifier
      .fillMaxSize()
      .background(baseColor)
  ) {}
}

@Composable
fun BankCardNumber(cardNumber: String) {
  Row(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 32.dp),
    horizontalArrangement = Arrangement.SpaceBetween, // Space out the children evenly
    verticalAlignment = Alignment.CenterVertically // Center the children vertically
  ) {
    // Draw the first three groups of dots
    repeat(3) {
      BankCardDotGroup()
    }

    // Display the last four digits
    Text(
      text = cardNumber.takeLast(4),
      fontSize = 20.sp,
      letterSpacing = 1.sp,
      color = Color.White
    )
  }
}


@Composable
fun BankCardDotGroup() {
  Canvas(
    modifier = Modifier.width(48.dp),
    onDraw = { // You can adjust the width as needed
      val dotRadius = 4.dp.toPx()
      val spaceBetweenDots = 8.dp.toPx()
      for (i in 0 until 4) { // Draw four dots
        drawCircle(
          color = Color.White,
          radius = dotRadius,
          center = Offset(
            x = i * (dotRadius * 2 + spaceBetweenDots) + dotRadius,
            y = center.y
          )
        )
      }
    })
}


@Composable
fun BankCardLabelAndText(label: String, text: String) {
  Column(
    modifier = Modifier
      .wrapContentSize(),
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = label.uppercase(),
      fontWeight = FontWeight.W300,
      fontSize = 12.sp,
      letterSpacing = 1.sp,
      color = Color.White
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = text,
      fontWeight = FontWeight.W400,
      fontSize = 16.sp,
      letterSpacing = 1.sp,
      color = Color.White
    )
  }
}

@Composable
fun SpaceWrapper(
  modifier: Modifier = Modifier,
  space: Dp,
  top: Boolean = false,
  right: Boolean = false,
  bottom: Boolean = false,
  left: Boolean = false,
  content: @Composable BoxScope.() -> Unit
) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
      .then(if (top) Modifier.padding(top = space) else Modifier)
      .then(if (right) Modifier.padding(end = space) else Modifier)
      .then(if (bottom) Modifier.padding(bottom = space) else Modifier)
      .then(if (left) Modifier.padding(start = space) else Modifier)
  ) {
    content()
  }
}

// Take a sneak peek with @Preview
@Composable
@Preview
fun BankCardUiPreview() {
  Box(
    Modifier
      .padding(16.dp)
  ) {
    BankCardUi()
  }
}