package com.mathforkids.ui.screen.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mathforkids.domain.model.AnswerType
import com.mathforkids.ui.components.AnimatedButton
import com.mathforkids.ui.screen.exercise.components.*
import com.mathforkids.util.HebrewStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    onSessionComplete: (correct: Int, total: Int) -> Unit,
    onBack: () -> Unit,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSessionComplete) {
        if (uiState.isSessionComplete) {
            onSessionComplete(uiState.correctCount, uiState.exercises.size)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${HebrewStrings.QUESTION} ${uiState.currentIndex + 1} ${HebrewStrings.OF} ${uiState.exercises.size}"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = HebrewStrings.BACK_TO_TOPICS
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExerciseProgressBar(progress = uiState.progress)

            Spacer(modifier = Modifier.height(32.dp))

            // Question
            val exercise = uiState.currentExercise
            if (exercise != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = exercise.question,
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Answer input
                when (exercise.answerType) {
                    AnswerType.MULTIPLE_CHOICE -> {
                        MultipleChoiceAnswer(
                            choices = exercise.choices,
                            selectedChoice = uiState.selectedChoice,
                            showFeedback = uiState.showFeedback,
                            correctAnswer = exercise.correctAnswer,
                            onChoiceSelected = viewModel::onChoiceSelected
                        )
                    }
                    AnswerType.FREE_FORM -> {
                        FreeFormAnswer(
                            value = uiState.userInput,
                            showFeedback = uiState.showFeedback,
                            isCorrect = uiState.lastAnswerCorrect,
                            onValueChanged = viewModel::onUserInputChanged,
                            onSubmit = viewModel::submitAnswer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Feedback
                FeedbackAnimation(
                    isCorrect = uiState.lastAnswerCorrect,
                    correctAnswer = exercise.correctAnswerDisplay,
                    visible = uiState.showFeedback
                )

                Spacer(modifier = Modifier.weight(1f))

                // Action button
                if (!uiState.showFeedback) {
                    val canSubmit = when (exercise.answerType) {
                        AnswerType.MULTIPLE_CHOICE -> uiState.selectedChoice != null
                        AnswerType.FREE_FORM -> uiState.userInput.isNotBlank()
                    }
                    AnimatedButton(
                        text = HebrewStrings.SUBMIT,
                        onClick = viewModel::submitAnswer,
                        enabled = canSubmit,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    AnimatedButton(
                        text = HebrewStrings.NEXT,
                        onClick = viewModel::nextQuestion,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
