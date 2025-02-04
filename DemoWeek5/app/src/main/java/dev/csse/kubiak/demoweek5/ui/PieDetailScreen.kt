package dev.csse.kubiak.demoweek5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek5.Pie
import dev.csse.kubiak.demoweek5.ui.theme.DemoWeek5Theme

@Composable
fun PieDetailScreen(pie: Pie, modifier: Modifier = Modifier, onUpClick: () -> Unit  = {}) {
  Scaffold(
    topBar = { PieAppBar(
      title =  pie.name,
      onUpClick = onUpClick
    )}
  ){ innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(innerPadding),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Image(
        modifier = Modifier.fillMaxWidth().aspectRatio(1f).padding(20.dp),
        painter = painterResource(pie.resourceId),
        contentDescription = pie.name
      )
      Card(modifier = Modifier.weight(1f).padding(20.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
          Row {
            Text("Filling", modifier = Modifier.width(60.dp))
            Text(pie.filling, modifier = Modifier.weight(1f))
          }
          Row {
            Text("Crust", modifier = Modifier.width(60.dp))
            Text(pie.crust, modifier = Modifier.weight(1f))
          }
        }
      }
    }
  }
}

@Preview
@Composable
fun PieDetailScreenDemo() {
  val pieViewModel = viewModel<PieViewModel>()

  if (pieViewModel.pieList.isEmpty())
    pieViewModel.createSampleData()

  val pie = pieViewModel.getPies()[0]

  DemoWeek5Theme {
    PieDetailScreen(
      pie,
      modifier = Modifier.padding(4.dp, 8.dp)
    )
  }
}
