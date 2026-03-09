package com.mathforkids.ui.screen.exercise.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val NumeratorColor = Color(0xFF5C6BC0)
private val BarColor = Color(0xFF333333)

@Composable
fun FractionAnswer(
    numerator: String,
    denominator: String,
    showFeedback: Boolean,
    isCorrect: Boolean,
    onNumeratorChanged: (String) -> Unit,
    onDenominatorChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Numerator input
            OutlinedTextField(
                value = numerator,
                onValueChange = { if (!showFeedback) onNumeratorChanged(it.filter { c -> c.isDigit() }) },
                modifier = Modifier.width(100.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = NumeratorColor
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                enabled = !showFeedback,
                isError = showFeedback && !isCorrect
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Fraction bar
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(3.dp)
                    .background(BarColor)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Denominator input
            OutlinedTextField(
                value = denominator,
                onValueChange = { if (!showFeedback) onDenominatorChanged(it.filter { c -> c.isDigit() }) },
                modifier = Modifier.width(100.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = BarColor
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (!showFeedback && numerator.isNotBlank() && denominator.isNotBlank()) {
                            onSubmit()
                        }
                    }
                ),
                singleLine = true,
                enabled = !showFeedback,
                isError = showFeedback && !isCorrect
            )
        }
    }
}
