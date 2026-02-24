package com.mathforkids.ui.screen.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathforkids.domain.model.Achievement
import com.mathforkids.domain.usecase.GetAchievementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    getAchievementsUseCase: GetAchievementsUseCase
) : ViewModel() {

    val achievements: StateFlow<List<Achievement>> = getAchievementsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
