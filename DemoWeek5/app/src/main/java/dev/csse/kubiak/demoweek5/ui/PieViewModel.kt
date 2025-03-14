package dev.csse.kubiak.demoweek5.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import dev.csse.kubiak.demoweek5.Pie
import dev.csse.kubiak.demoweek5.PieShop
import dev.csse.kubiak.demoweek5.R
import kotlin.String

private val samplePies = arrayOfPies()

class PieGridViewModel : ViewModel() {
  private val pieList = mutableStateListOf<Pie>()

  fun hasPies() : Boolean {
    return !getPies().isEmpty()
  }

  fun getPies() : List<Pie> {
    return pieList
  }

  fun createSampleData() {
    for (pie in samplePies)
      pieList.add(pie)
  }
}

class PieDetailViewModel: ViewModel() {
  private var currentPie: Pie by mutableStateOf<Pie>(Pie())

  fun getCurrent() : Pie {
    return currentPie
  }

  fun loadById(id: Int) {
    val found = samplePies.find { pie -> pie.id == id }
    currentPie = found ?: Pie()
  }
}

fun arrayOfPies() : List<Pie> {
  return arrayOf(
    "Almond Cream Pie",
    "Apple Pie",
    "Apricot Cream Pie",
    "Apricot Pie",
    "Banana Cream Pie",
    "Banana Foster Pie",
    "Banoffee Pie",
    "Black Bottom Pie",
    "Blackberry Lemon Pie",
    "Blackberry Peach Pie",
    "Blackberry Pie",
    "Blueberry Pie",
    "Boston Cream Pie",
    "Brown Sugar Pie",
    "Buckeye Pie",
    "Buttermilk Pie",
    "Caramel Apple Pie",
    "Caramel Pie",
    "Cherry Almond Pie",
    "Cherry Pie",
    "Chocolate Banana Pie",
    "Chocolate Bourbon Pecan Pie",
    "Chocolate Caramel Pie",
    "Chocolate Chess Pie",
    "Chocolate Chip Pie",
    "Chocolate Cream Pie",
    "Chocolate Hazelnut Pie",
    "Chocolate Lavender Pie",
    "Chocolate Peanut Butter Pie",
    "Chocolate Silk Pie",
    "Coconut Cream Pie",
    "Coconut Custard Pie",
    "Coconut Pineapple Pie",
    "Cranberry Orange Pie",
    "Cranberry Pie",
    "Cream Cheese Pie",
    "Creamy Lemon Pie",
    "Custard Pie",
    "Dark Chocolate Pie",
    "Derby Pie",
    "Double Berry Pie",
    "Dutch Apple Pie",
    "Egg Custard Pie",
    "Elderberry Pie",
    "Grape Pie",
    "Grasshopper Pie",
    "Honey Pie",
    "Key Lime Pie",
    "Kiwi Pie",
    "Lemon Buttermilk Pie",
    "Lemon Chess Pie",
    "Lemon Icebox Pie",
    "Lemon Meringue Pie",
    "Lime Meringue Pie",
    "Mango Pie",
    "Maple Cream Pie",
    "Maple Pie",
    "Mississippi Mud Pie",
    "Mixed Berry Pie",
    "Mixed Citrus Pie",
    "Mocha Pie",
    "Nectarine Pie",
    "Oatmeal Pie",
    "Orange Cream Pie",
    "Peach Pie",
    "Peach Raspberry Pie",
    "Peanut Butter Pie",
    "Pear Almond Pie",
    "Pear Pie",
    "Pecan Chocolate Pie",
    "Persimmon Pie",
    "Pineapple Pie",
    "Plum Pie",
    "Pumpkin Pie",
    "Raisin Pie",
    "Raspberry Peach Pie",
    "Raspberry Pie",
    "Rhubarb Pie",
    "Ricotta Pie",
    "Rum Raisin Pie",
    "S’mores Pie",
    "Salted Caramel Pie",
    "Shaker Lemon Pie",
    "Shoofly Pie",
    "Shortcrust Lemon Pie",
    "Shredded Coconut Pie",
    "Sour Cherry Pie",
    "Sour Cream Raisin Pie",
    "Spiced Apple Pie",
    "Spiced Pear Pie",
    "Squash Pie",
    "Strawberry Banana Pie",
    "Strawberry Cream Pie",
    "Strawberry Custard Pie",
    "Strawberry Rhubarb Pie",
    "Sugar Cream Pie",
    "Sweet Corn Pie",
    "Sweet Orange Pie",
    "Sweet Potato Pie",
    "Tart Cherry Pie",
    "Toffee Pie",
    "Treacle Pie",
    "Triple Berry Pie",
    "Tropical Coconut Pie",
    "Tropical Fruit Pie",
    "Turtle Pie",
    "Vanilla Cream Pie",
    "Walnut Pie",
    "Watermelon Pie",
    "White Chocolate Pie",
    "White Chocolate Raspberry Pie",
    "Wild Berry Pie",
    "Wild Blueberry Pie",
    "Winterberry Pie",
    "Yam Pie"
  ).mapIndexed { i, s ->
    val words = s.split(" ")
    Pie(
      id = i,
      name = s,
      resourceId = R.drawable.apple_pie,
      crust = "shortcrust",
      filling = words[words.size - 2].toLowerCase(Locale.current),
      pieShops = listOf(
        PieShop(
          name = "Bramble Pie Company",
          website = "https://bramblepiecompany.com",
          location = "geo:35.489596,-120.668665?q=Bramble%20Pie%20Company"
        )
      )
    )
  }
}