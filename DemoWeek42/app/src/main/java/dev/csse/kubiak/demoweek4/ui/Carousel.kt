package dev.csse.kubiak.demoweek4.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.csse.kubiak.demoweek4.R

data class City(
  var name: String,
  var resourceId: Int,
  var description: String = ""
)

@Composable
fun Carousel(cities: List<City>) {
  LazyRow(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    item {
      Text("Cities", style = MaterialTheme.typography.titleLarge )
    }
    items(cities) { message ->
      CityCard(message)
    }
    item {
      Text("See all…")
    }
  }
}

@Composable
fun CityCard(city: City) {
  Card(modifier = Modifier.size(100.dp, 120.dp)
    ) {
    Column(modifier = Modifier
      .background(color=Color(0xffffbbbb))
      .padding(10.dp),
      horizontalAlignment = Alignment.CenterHorizontally) {
      Image(
        painter = painterResource(city.resourceId),
        contentDescription = city.description,
        modifier = Modifier.fillMaxWidth().aspectRatio(1f)
      )
      Text(city.name)
    }
  }
}

@Preview
@Composable
fun CarouselPreview() {
  val cities = listOf(
    City("Barcelona", R.drawable.barcelona_sagrada_familia, "sagrada familia"),
    City("Dubai", R.drawable.dubai_burj_al_arab, "burj al arab"),
    City("Istanbul", R.drawable.istanbul_hagia_sophia, "hagia sophia"),
    City("Kuala Lumpur", R.drawable.kuala_lumpur_petronas_twin_towers, "petronas twin towers"),
    City("London", R.drawable.london_big_ben, "big ben"),
    City("Moscow", R.drawable.moscow_st_basils_cathedral, "st basil's cathedral"),
    City("New York City", R.drawable.new_york_statue_of_liberty, "statue of liberty"),
    City("Paris", R.drawable.paris_eiffel, "the eiffel tower"),
    City("Rio de Janeiro", R.drawable.rio_de_janiero_christ_the_redeemer, "christ the redeemer"),
    City("Rome", R.drawable.rome_colosseum, "the colosseum"),
    City("Seattle", R.drawable.seattle_needle_space, "space needle"),
    City("Sydney", R.drawable.sydney_opera_house, "sydney opera house")
  )

  Carousel(cities)
}