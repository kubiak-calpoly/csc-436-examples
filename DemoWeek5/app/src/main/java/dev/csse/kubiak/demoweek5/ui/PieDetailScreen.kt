package dev.csse.kubiak.demoweek5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek5.Pie
import dev.csse.kubiak.demoweek5.ui.theme.DemoWeek5Theme

@Composable
fun PieDetailScreen(pie: Pie, modifier: Modifier = Modifier) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(12.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Text(

      text = pie.name,
      style = MaterialTheme.typography.titleLarge
    )
    Image(
      painter = painterResource(pie.resourceId),
      contentDescription = pie.name
    )

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
