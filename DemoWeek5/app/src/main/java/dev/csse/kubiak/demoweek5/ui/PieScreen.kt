package dev.csse.kubiak.demoweek5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek5.Pie
import dev.csse.kubiak.demoweek5.ui.theme.DemoWeek5Theme


@Composable
fun PieScreen(
  modifier: Modifier = Modifier,
  pieViewModel: PieViewModel = viewModel()
) {
  

  LazyColumn(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
      items(
        items = pieViewModel.getPies(),
        key = { it.name }
      ) { pie ->
        PieChip(pie, onClick = {
          pieViewModel.setCurrent(pie)
        })
      }
  }
}

@Composable
fun PieChip(
  pie: Pie,
  onClick: (Pie) -> Unit = {}
) {
  Card( onClick = { onClick(pie) } ) {
    Column(
      modifier = Modifier
        .fillMaxHeight()
        .padding(12.dp),
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = pie.name,
        style = MaterialTheme.typography.titleLarge
      )
    }

  }
}


@Preview
@Composable
fun PieScreenDemo() {
  val todoViewModel = viewModel<PieViewModel>()

  if (todoViewModel.pieList.isEmpty())
    todoViewModel.createSampleData()

  DemoWeek5Theme {
    PieScreen(modifier = Modifier.padding(4.dp, 8.dp))
  }
}
