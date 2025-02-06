package dev.csse.kubiak.demoweek5

data class Pie(
  val id: Int = -1,
  val name: String = "Mystery Pie",
  val resourceId: Int = R.drawable.apple_pie,
  val crust: String = "shortcrust",
  val filling: String = "mystery",
  val website: String = "https://en.wikipedia.org/wiki/Apple_pie",
  val pieShop: String = "Bramble Pie Company",
  val location: String = "geo:35.489596,-120.668665?q=Bramble%20Pie%20Company"
)