package dev.csse.kubiak.demoweek5.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import dev.csse.kubiak.demoweek5.Pie
import dev.csse.kubiak.demoweek5.ui.theme.DemoWeek5Theme

@Composable
fun PieDetailScreen(
  pie: Pie,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Image(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .padding(20.dp),
      painter = painterResource(pie.resourceId),
      contentDescription = pie.name
    )
    Card(
      modifier = Modifier
        .padding(20.dp)
    ) {
      Column(modifier = Modifier.padding(8.dp)) {
        Row {
          Text("Filling", modifier = Modifier.width(80.dp))
          Text(
            pie.filling, modifier = Modifier.weight(1f), textAlign = TextAlign.End
          )
        }
        Row {
          Text("Crust", modifier = Modifier.width(80.dp))
          Text(
            pie.crust, modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
          )
        }
      }
    }
  }
}


@Preview
@Composable
fun PieDetailScreenDemo() {
  val pie = arrayOfPies()[0]

  DemoWeek5Theme {
    PieDetailScreen(
      pie,
      modifier = Modifier.padding(4.dp, 8.dp)
    )
  }
}
