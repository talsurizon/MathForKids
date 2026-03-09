package com.mathforkids.ui.screen.exercise.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val FractionNumeratorColor = Color(0xFF5C6BC0) // Indigo

@Composable
fun FractionView(
    numerator: String,
    denominator: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 32.sp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = numerator,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = FractionNumeratorColor,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .width(fontSize.value.dp * 1.5f)
                .height(2.dp)
                .background(Color(0xFF333333))
        )
        Text(
            text = denominator,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FractionQuestionDisplay(
    question: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 32.sp
) {
    val tokens = question.split(" ")

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        tokens.forEach { token ->
            if (token.contains("/") && token.matches(Regex("\\d+/\\d+"))) {
                val parts = token.split("/")
                FractionView(
                    numerator = parts[0],
                    denominator = parts[1],
                    fontSize = fontSize
                )
            } else {
                Text(
                    text = " $token ",
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
