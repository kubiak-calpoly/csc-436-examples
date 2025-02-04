package dev.csse.kubiak.demoweek5.ui

import android.content.res.Configuration
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
import androidx.compose.material3.Scaffold
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
) {
  val config = LocalConfiguration.current

  Scaffold(
    topBar = { PieAppBar("Pie-terest")}
  ) { innerPadding ->

    if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
      Column(
        modifier = Modifier.padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        PieGrid(
          pies, modifier = Modifier.weight(1f),
          onSelect = onPieSelection
        )
        if (selectedPie != null) {
          PieCard(
            pie = selectedPie,
            modifier = Modifier.weight(1f)
          )
        }
      }
    } else {
      Row(
        modifier = Modifier.padding(innerPadding)
      ) {
        PieGrid(
          pies, modifier = Modifier.weight(1f),
          onSelect = onPieSelection
        )
        if (selectedPie != null) {
          PieCard(
            pie = selectedPie,
            modifier = Modifier.weight(1f)
          )
        }
      }
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
fun PieCard(pie: Pie, modifier: Modifier = Modifier) {
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
  val pieViewModel = viewModel<PieViewModel>()

  if (pieViewModel.pieList.isEmpty())
    pieViewModel.createSampleData()

  DemoWeek5Theme {
    PieGridScreen(
      pieViewModel.getPies(),
      onPieSelection = {}
    )
  }
}
