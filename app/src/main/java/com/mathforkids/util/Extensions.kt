package com.mathforkids.util

fun Double.toDisplayString(): String =
    if (this == toLong().toDouble()) toLong().toString() else toString()

fun Int.toStars(): Int = when {
    this == 100 -> 3
    this >= 80 -> 2
    this >= 60 -> 1
    else -> 0
}

fun calculateScorePercent(correct: Int, total: Int): Int =
    if (total > 0) (correct * 100) / total else 0
