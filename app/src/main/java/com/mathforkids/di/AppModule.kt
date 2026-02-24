package com.mathforkids.di

import android.content.Context
import androidx.room.Room
import com.mathforkids.data.local.AppDatabase
import com.mathforkids.data.local.dao.AchievementDao
import com.mathforkids.data.local.dao.ProgressDao
import com.mathforkids.data.repository.AchievementRepositoryImpl
import com.mathforkids.data.repository.ProgressRepositoryImpl
import com.mathforkids.domain.repository.AchievementRepository
import com.mathforkids.domain.repository.ProgressRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "math_for_kids.db"
        ).build()

    @Provides
    fun provideProgressDao(db: AppDatabase): ProgressDao = db.progressDao()

    @Provides
    fun provideAchievementDao(db: AppDatabase): AchievementDao = db.achievementDao()

    @Provides
    @Singleton
    fun provideProgressRepository(dao: ProgressDao): ProgressRepository =
        ProgressRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideAchievementRepository(dao: AchievementDao): AchievementRepository =
        AchievementRepositoryImpl(dao)
}
