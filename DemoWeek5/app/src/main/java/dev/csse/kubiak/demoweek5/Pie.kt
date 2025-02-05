package dev.csse.kubiak.demoweek5

data class Pie(
  val id: Int = -1,
  val name: String = "Mystery Pie",
  val resourceId: Int = R.drawable.apple_pie,
  val crust: String = "shortcrust",
  val filling: String = "mystery"
)