package dev.csse.kubiak.demoweek3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.csse.kubiak.demoweek3.ui.theme.DemoWeek3Theme

@Preview
@Composable fun ThemedFontSample () {
  DemoWeek3Theme { FontSample() }
}


@Composable fun FontSample () {
  Column {
    Text(
      text = "I have a dream",
      style = MaterialTheme.typography.titleLarge
    )
    Text(
      text = stringResource(R.string.mlk_dream),
      style = MaterialTheme.typography.bodyLarge
    )
  }
}