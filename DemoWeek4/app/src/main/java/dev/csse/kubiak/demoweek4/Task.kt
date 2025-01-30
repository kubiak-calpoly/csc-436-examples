package dev.csse.kubiak.demoweek4

import java.util.UUID

data class Task (
   var id: UUID = UUID.randomUUID(),
   var body: String = "",
   var completed: Boolean = false,
   var tags: List<String> = listOf()
)