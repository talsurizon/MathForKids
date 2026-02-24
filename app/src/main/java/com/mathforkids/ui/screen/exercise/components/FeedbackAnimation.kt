package com.mathforkids.ui.screen.exercise.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathforkids.ui.theme.CorrectGreen
import com.mathforkids.ui.theme.WrongRed
import com.mathforkids.util.HebrewStrings

@Composable
fun FeedbackAnimation(
    isCorrect: Boolean,
    correctAnswer: String,
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "feedbackScale"
    )

    AnimatedVisibility(
        visible = visible,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut(),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scale)
        ) {
            Text(
                text = if (isCorrect) "✅" else "❌",
                fontSize = 48.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isCorrect) HebrewStrings.CORRECT else HebrewStrings.WRONG,
                style = MaterialTheme.typography.headlineMedium,
                color = if (isCorrect) CorrectGreen else WrongRed
            )
            if (!isCorrect) {
                Text(
                    text = "${HebrewStrings.THE_ANSWER_IS}: $correctAnswer",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
