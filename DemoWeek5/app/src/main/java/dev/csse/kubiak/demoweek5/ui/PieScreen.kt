package dev.csse.kubiak.demoweek5.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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
import androidx.compose.ui.platform.LocalConfiguration
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
  val pies = pieViewModel.getPies()
  val config = LocalConfiguration.current

  if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
    Column(
      modifier = modifier,
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      PieGrid(
        pies, modifier = Modifier.weight(1f),
        onSelect = pieViewModel::setCurrent
      )
      PieCard(
        pie = pieViewModel.getCurrent(),
        modifier = Modifier.weight(1f)
      )
    }
  } else {
    Row {
      PieGrid(
        pies, modifier = Modifier.weight(1f),
        onSelect = pieViewModel::setCurrent
      )
      PieCard(
        pie = pieViewModel.getCurrent(),
        modifier = Modifier.weight(1f)
      )
    }
  }
}


@Composable
fun PieGrid(
  pies: List<Pie>, modifier: Modifier = Modifier,
  onSelect: (Pie) -> Unit
) {
  LazyHorizontalStaggeredGrid(
    modifier = modifier,
    rows = StaggeredGridCells.Adaptive(100.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalItemSpacing = 4.dp,
  ) {
    items(
      items = pies,
      key = { it.name }
    ) { pie ->
      PieChip(pie, onClick = onSelect)
    }
  }

}

@Composable
fun PieChip(
  pie: Pie,
  onClick: (Pie) -> Unit = {}
) {
  Card(onClick = { onClick(pie) }) {
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

@Composable
fun PieCard(modifier: Modifier = Modifier, pie: Pie) {
  Card(modifier = modifier) {
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
