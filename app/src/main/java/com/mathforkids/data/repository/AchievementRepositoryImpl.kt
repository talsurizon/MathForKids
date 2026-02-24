package com.mathforkids.data.repository

import com.mathforkids.data.local.dao.AchievementDao
import com.mathforkids.data.local.entity.AchievementEntity
import com.mathforkids.domain.model.Achievement
import com.mathforkids.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AchievementRepositoryImpl @Inject constructor(
    private val achievementDao: AchievementDao
) : AchievementRepository {

    override fun getAllAchievements(): Flow<List<Achievement>> =
        achievementDao.getAllAchievements().map { entities ->
            Achievement.ALL.map { definition ->
                val entity = entities.find { it.id == definition.id }
                definition.copy(
                    isUnlocked = entity?.isUnlocked ?: false,
                    unlockedAt = entity?.unlockedAt
                )
            }
        }

    override fun getUnlockedCount(): Flow<Int> =
        achievementDao.getUnlockedCount()

    override suspend fun unlockAchievement(id: String) {
        val existing = achievementDao.getAchievement(id)
        if (existing == null || !existing.isUnlocked) {
            achievementDao.upsertAchievement(
                AchievementEntity(
                    id = id,
                    isUnlocked = true,
                    unlockedAt = System.currentTimeMillis()
                )
            )
        }
    }

    override suspend fun isUnlocked(id: String): Boolean =
        achievementDao.getAchievement(id)?.isUnlocked ?: false
}
