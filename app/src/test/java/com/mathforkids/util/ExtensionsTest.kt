package com.mathforkids.util

import org.junit.Assert.*
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `toDisplayString shows integer for whole numbers`() {
        assertEquals("5", 5.0.toDisplayString())
        assertEquals("100", 100.0.toDisplayString())
    }

    @Test
    fun `toDisplayString shows decimal for fractional numbers`() {
        assertEquals("5.5", 5.5.toDisplayString())
        assertEquals("3.14", 3.14.toDisplayString())
    }

    @Test
    fun `toStars returns 3 for 100 percent`() {
        assertEquals(3, 100.toStars())
    }

    @Test
    fun `toStars returns 2 for 80-99 percent`() {
        assertEquals(2, 80.toStars())
        assertEquals(2, 90.toStars())
        assertEquals(2, 99.toStars())
    }

    @Test
    fun `toStars returns 1 for 60-79 percent`() {
        assertEquals(1, 60.toStars())
        assertEquals(1, 70.toStars())
        assertEquals(1, 79.toStars())
    }

    @Test
    fun `toStars returns 0 for below 60 percent`() {
        assertEquals(0, 59.toStars())
        assertEquals(0, 0.toStars())
    }

    @Test
    fun `calculateScorePercent computes correctly`() {
        assertEquals(100, calculateScorePercent(10, 10))
        assertEquals(50, calculateScorePercent(5, 10))
        assertEquals(0, calculateScorePercent(0, 10))
        assertEquals(0, calculateScorePercent(0, 0))
    }
}
