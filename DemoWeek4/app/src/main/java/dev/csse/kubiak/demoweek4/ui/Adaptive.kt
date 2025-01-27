package dev.csse.kubiak.demoweek4.ui


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text2.input.maxLengthInChars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.csse.kubiak.demoweek4.R

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun AdaptiveRow(
  modifier: Modifier = Modifier,
  weight1: Float? = null,
  weight2: Float? = null,
  ) {
  Row(modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
    Column(modifier = if (weight1 == null) Modifier else Modifier.weight(weight1) ) {
      Text(
        "Metamorphosis",
        style = MaterialTheme.typography.titleLarge
      )
      Text(
        "by Franz Kafka",
        style = MaterialTheme.typography.titleSmall
      )
    }
    Column(modifier = if (weight2 == null) Modifier else Modifier.weight(weight2)) {
      Text(stringResource(R.string.metamorphosis_p1))
    }
  }
}


@Preview
@Composable
fun NarrowRowPreview(){
  AdaptiveRow(modifier =  Modifier.width(300.dp))
}

@Preview
@Composable
fun WideRowPreview(){
  AdaptiveRow(modifier =  Modifier.fillMaxWidth() )
}

@Preview
@Composable
fun OneTwoRowPreview() {
  AdaptiveRow(modifier =  Modifier.fillMaxWidth(),
    weight1 = 1f,
    weight2 = 2f
    )

}