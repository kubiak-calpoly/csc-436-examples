package dev.csse.kubiak.demoweek4.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dev.csse.kubiak.demoweek4.Task

class ToDoViewModel : ViewModel() {
   val taskList = mutableStateListOf<Task>()
   val tagList = mutableStateListOf<String>("Study", "Fun", "Work", "Food")

   fun addTask(task: Task) {
      taskList.add(0, task)
   }

   fun deleteTask(task: Task) {
      taskList.remove(task)
   }

   val completedTasksExist: Boolean
      get() = taskList.count { it.completed } > 0

   fun deleteCompletedTasks() {
      // Remove only tasks that are completed
      taskList.removeIf { it.completed }
   }

   fun createTestTasks(numTasks: Int = 10) {
      // Add tasks for testing purposes
      for (i in 1..numTasks) {
         addTask(Task(body = "task $i", tags =  listOf("Work")))
      }
   }

   fun toggleTaskCompleted(task: Task) {
      // Observer of MutableList not notified when changing a property, so
      // need to replace element in the list for notification to go through
      val index = taskList.indexOf(task)
      taskList[index] = taskList[index].copy(completed = !task.completed)
   }

}