package dev.csse.kubiak.demoweek5.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.kubiak.demoweek5.Pie
import dev.csse.kubiak.demoweek5.ui.theme.DemoWeek5Theme


@Composable
fun PieGridScreen(
  pies: List<Pie>,
  selectedPie: Pie? = null,
  onPieSelection: (Pie) -> Unit,
  modifier: Modifier = Modifier,
) {
  val config = LocalConfiguration.current

  PieGrid(
    pies, modifier = modifier,
    onSelect = onPieSelection
  )
}


@Composable
fun PieGrid(
  pies: List<Pie>, modifier: Modifier = Modifier,
  onSelect: (Pie) -> Unit
) {

  Log.d("PieGrid", "Number of Pies: ${pies.size}")

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



@Preview
@Composable
fun PieScreenDemo() {
  val pieViewModel = viewModel<PieListViewModel>()

  if (!pieViewModel.hasPies())
    pieViewModel.createSampleData()

  val pies = pieViewModel.getPies()

  DemoWeek5Theme {
    PieGridScreen(pies,
      onPieSelection = {},
      modifier = Modifier.padding(4.dp, 8.dp)
    )
  }
}
