package com.mathforkids.ui.screen.exercise

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathforkids.domain.model.*
import com.mathforkids.domain.usecase.CheckAnswerUseCase
import com.mathforkids.domain.usecase.GenerateExercisesUseCase
import com.mathforkids.domain.usecase.SaveSessionResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExerciseUiState(
    val exercises: List<Exercise> = emptyList(),
    val currentIndex: Int = 0,
    val results: List<ExerciseResult> = emptyList(),
    val userInput: String = "",
    val selectedChoice: Double? = null,
    val showFeedback: Boolean = false,
    val lastAnswerCorrect: Boolean = false,
    val isSessionComplete: Boolean = false,
    val questionStartTime: Long = System.currentTimeMillis()
) {
    val currentExercise: Exercise?
        get() = exercises.getOrNull(currentIndex)

    val progress: Float
        get() = if (exercises.isEmpty()) 0f
        else (currentIndex.toFloat() / exercises.size)

    val correctCount: Int
        get() = results.count { it.isCorrect }
}

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val generateExercisesUseCase: GenerateExercisesUseCase,
    private val checkAnswerUseCase: CheckAnswerUseCase,
    private val saveSessionResultsUseCase: SaveSessionResultsUseCase
) : ViewModel() {

    private val gradeLevel: Int = savedStateHandle["gradeLevel"] ?: 1
    private val topicName: String = savedStateHandle["topicName"] ?: ""
    private val grade = Grade.fromLevel(gradeLevel)
    private val topic = Topic.valueOf(topicName)

    private val _uiState = MutableStateFlow(ExerciseUiState())
    val uiState: StateFlow<ExerciseUiState> = _uiState.asStateFlow()

    init {
        generateExercises()
    }

    private fun generateExercises() {
        val exercises = generateExercisesUseCase(grade, topic)
        _uiState.update {
            it.copy(
                exercises = exercises,
                currentIndex = 0,
                results = emptyList(),
                questionStartTime = System.currentTimeMillis()
            )
        }
    }

    fun onUserInputChanged(input: String) {
        _uiState.update { it.copy(userInput = input) }
    }

    fun onChoiceSelected(choice: Double) {
        _uiState.update { it.copy(selectedChoice = choice) }
    }

    fun submitAnswer() {
        val state = _uiState.value
        val exercise = state.currentExercise ?: return

        val userAnswer = when (exercise.answerType) {
            AnswerType.MULTIPLE_CHOICE -> state.selectedChoice
            AnswerType.FREE_FORM -> state.userInput.toDoubleOrNull()
        }

        val timeSpent = System.currentTimeMillis() - state.questionStartTime
        val result = checkAnswerUseCase(exercise, userAnswer, timeSpent)

        _uiState.update {
            it.copy(
                results = it.results + result,
                showFeedback = true,
                lastAnswerCorrect = result.isCorrect
            )
        }
    }

    fun nextQuestion() {
        val state = _uiState.value
        val nextIndex = state.currentIndex + 1

        if (nextIndex >= state.exercises.size) {
            _uiState.update { it.copy(isSessionComplete = true) }
            saveResults()
        } else {
            _uiState.update {
                it.copy(
                    currentIndex = nextIndex,
                    userInput = "",
                    selectedChoice = null,
                    showFeedback = false,
                    questionStartTime = System.currentTimeMillis()
                )
            }
        }
    }

    private fun saveResults() {
        viewModelScope.launch {
            saveSessionResultsUseCase(grade, topic, _uiState.value.results)
        }
    }
}
