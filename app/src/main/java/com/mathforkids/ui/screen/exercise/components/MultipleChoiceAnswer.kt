package com.mathforkids.ui.screen.exercise.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mathforkids.ui.theme.CorrectGreen
import com.mathforkids.util.Extensions

@Composable
fun MultipleChoiceAnswer(
    choices: List<Double>,
    selectedChoice: Double?,
    showFeedback: Boolean,
    correctAnswer: Double,
    onChoiceSelected: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        choices.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { choice ->
                    val isSelected = choice == selectedChoice
                    val isCorrect = choice == correctAnswer
                    val containerColor = when {
                        showFeedback && isCorrect -> CorrectGreen
                        showFeedback && isSelected && !isCorrect ->
                            MaterialTheme.colorScheme.error
                        isSelected -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                    val textColor = when {
                        showFeedback && isCorrect -> MaterialTheme.colorScheme.onPrimary
                        showFeedback && isSelected -> MaterialTheme.colorScheme.onError
                        isSelected -> MaterialTheme.colorScheme.onPrimary
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }

                    Button(
                        onClick = { if (!showFeedback) onChoiceSelected(choice) },
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = containerColor,
                            contentColor = textColor
                        ),
                        enabled = !showFeedback || isSelected || isCorrect
                    ) {
                        val display = if (choice == choice.toLong().toDouble()) {
                            choice.toLong().toString()
                        } else choice.toString()
                        Text(
                            text = display,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        }
    }
}
