package dev.csse.kubiak.demoweek4

var lastTaskId: Int = 0

data class Task (
   val id: Int = lastTaskId++,
   val body: String = "",
   val completed: Boolean = false,
   // var tags: List<String> = listOf()
)