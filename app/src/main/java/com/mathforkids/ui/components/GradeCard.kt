package com.mathforkids.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathforkids.domain.model.Grade
import com.mathforkids.ui.theme.*

@Composable
fun GradeCard(
    grade: Grade,
    bestStars: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = gradeColor(grade)

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = grade.hebrewName,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            StarRating(stars = bestStars, size = 24)
        }
    }
}

fun gradeColor(grade: Grade): Color = when (grade) {
    Grade.GRADE_1 -> Grade1Color
    Grade.GRADE_2 -> Grade2Color
    Grade.GRADE_3 -> Grade3Color
    Grade.GRADE_4 -> Grade4Color
    Grade.GRADE_5 -> Grade5Color
    Grade.GRADE_6 -> Grade6Color
}
