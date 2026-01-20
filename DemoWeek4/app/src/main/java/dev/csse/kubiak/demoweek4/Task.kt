package dev.csse.kubiak.demoweek4

import androidx.compose.runtime.Stable

var lastTaskId: Int = 0

data class Task (
   val id: Int = lastTaskId++,
   val body: String = "",
   val completed: Boolean = false,
)