package com.mathforkids.domain.usecase

import com.mathforkids.domain.model.Achievement
import com.mathforkids.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAchievementsUseCase @Inject constructor(
    private val achievementRepository: AchievementRepository
) {
    operator fun invoke(): Flow<List<Achievement>> =
        achievementRepository.getAllAchievements()

    fun unlockedCount(): Flow<Int> =
        achievementRepository.getUnlockedCount()
}
