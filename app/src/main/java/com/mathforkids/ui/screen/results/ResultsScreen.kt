package com.mathforkids.ui.screen.results

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathforkids.ui.components.AnimatedButton
import com.mathforkids.ui.components.StarRating
import com.mathforkids.ui.theme.CorrectGreen
import com.mathforkids.ui.theme.StarGold
import com.mathforkids.util.HebrewStrings
import com.mathforkids.util.calculateScorePercent
import com.mathforkids.util.toStars

@Composable
fun ResultsScreen(
    correct: Int,
    total: Int,
    onTryAgain: () -> Unit,
    onBackToTopics: () -> Unit,
    onBackToHome: () -> Unit
) {
    val scorePercent = calculateScorePercent(correct, total)
    val stars = scorePercent.toStars()

    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "resultsScale"
    )

    val message = when {
        stars == 3 -> HebrewStrings.EXCELLENT
        stars >= 2 -> HebrewStrings.GREAT_JOB
        else -> HebrewStrings.KEEP_TRYING
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = HebrewStrings.RESULTS,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Stars
        StarRating(
            stars = stars,
            size = 64,
            modifier = Modifier.scale(scale)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.headlineMedium,
            color = if (stars >= 2) CorrectGreen else MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Score card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Text(
                        text = "$correct / $total",
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Text(
                    text = HebrewStrings.CORRECT_ANSWERS,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Text(
                        text = "$scorePercent%",
                        fontSize = 36.sp,
                        color = StarGold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedButton(
            text = HebrewStrings.TRY_AGAIN,
            onClick = onTryAgain,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBackToTopics,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = HebrewStrings.BACK_TO_TOPICS)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onBackToHome) {
            Text(text = HebrewStrings.BACK_TO_HOME)
        }
    }
}
