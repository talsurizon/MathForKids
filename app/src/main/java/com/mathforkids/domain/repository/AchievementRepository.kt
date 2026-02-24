package com.mathforkids.domain.repository

import com.mathforkids.domain.model.Achievement
import kotlinx.coroutines.flow.Flow

interface AchievementRepository {
    fun getAllAchievements(): Flow<List<Achievement>>
    fun getUnlockedCount(): Flow<Int>
    suspend fun unlockAchievement(id: String)
    suspend fun isUnlocked(id: String): Boolean
}
