package dev.csse.kubiak.dbdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.csse.kubiak.dbdemo.StudyHelperApplication
import dev.csse.kubiak.dbdemo.data.Question
import dev.csse.kubiak.dbdemo.data.StudyRepository
import dev.csse.kubiak.dbdemo.data.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SubjectViewModel(private val studyRepo: StudyRepository) : ViewModel() {

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as StudyHelperApplication)
        SubjectViewModel(application.studyRepository)
      }
    }
  }

  private val selectedSubjects = MutableStateFlow(emptySet<Subject>())
  private val isSubjectDialogVisible = MutableStateFlow(false)

  val uiState: StateFlow<SubjectScreenUiState> = transformedFlow()
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000L),
      initialValue = SubjectScreenUiState(),
    )

  private fun transformedFlow() = selectedSubjects.flatMapLatest {
    set ->
    val questionsFlow =
      if (set.isEmpty()) flowOf(emptyList<Question>())
        else studyRepo.getQuestions(set.first().id)
    combine(
      studyRepo.getSubjects(),
      selectedSubjects,
      isSubjectDialogVisible,
      questionsFlow
    ) { subjects, selectSubs, dialogVisible, questions ->
      SubjectScreenUiState(
        subjectList = subjects,
        questionList = questions,
        selectedSubjects = selectSubs,
        isSubjectDialogVisible = dialogVisible
      )
    }
  }


   fun addSubject(title: String) {
      studyRepo.addSubject(Subject(title = title))
   }

   fun selectSubject(subject: Subject) {
      val selected = selectedSubjects.value.contains(subject)
      selectedSubjects.value = if (selected) {
         selectedSubjects.value.minus(subject)
      } else {
         selectedSubjects.value.plus(subject)
      }
   }

   fun hideCab() {
      selectedSubjects.value = emptySet()
   }

   fun deleteSelectedSubjects() {
      for (subject in selectedSubjects.value) {
         studyRepo.deleteSubject(subject)
      }
      hideCab()
   }

   fun showSubjectDialog() {
      isSubjectDialogVisible.value = true
   }

   fun hideSubjectDialog() {
      isSubjectDialogVisible.value = false
   }
}

data class SubjectScreenUiState(
  val subjectList: List<Subject> = emptyList(),
  val questionList: List<Question> = emptyList(),
  val selectedSubjects: Set<Subject> = emptySet(),
  val isCabVisible: Boolean = selectedSubjects.isNotEmpty(),
  val isSubjectDialogVisible: Boolean = false
)