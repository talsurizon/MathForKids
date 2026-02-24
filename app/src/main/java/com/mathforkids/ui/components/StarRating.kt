package com.mathforkids.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mathforkids.ui.theme.StarGold

@Composable
fun StarRating(
    stars: Int,
    maxStars: Int = 3,
    size: Int = 32,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(maxStars) { index ->
            Icon(
                imageVector = if (index < stars) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (index < stars) StarGold else Color.White.copy(alpha = 0.5f),
                modifier = Modifier.size(size.dp)
            )
        }
    }
}
