package com.mathforkids.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mathforkids.data.local.dao.AchievementDao
import com.mathforkids.data.local.dao.ProgressDao
import com.mathforkids.data.local.entity.AchievementEntity
import com.mathforkids.data.local.entity.ProgressEntity

@Database(
    entities = [ProgressEntity::class, AchievementEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao
    abstract fun achievementDao(): AchievementDao
}
