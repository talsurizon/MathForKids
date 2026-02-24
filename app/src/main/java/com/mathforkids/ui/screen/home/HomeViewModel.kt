package com.mathforkids.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Progress
import com.mathforkids.domain.usecase.GetProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeUiState(
    val grades: List<GradeInfo> = Grade.entries.map { GradeInfo(it) }
)

data class GradeInfo(
    val grade: Grade,
    val bestStars: Int = 0
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    getProgressUseCase: GetProgressUseCase
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = getProgressUseCase.all()
        .map { progressList ->
            HomeUiState(
                grades = Grade.entries.map { grade ->
                    val gradeProgress = progressList.filter { it.grade == grade }
                    GradeInfo(
                        grade = grade,
                        bestStars = gradeProgress.minOfOrNull { it.bestStars } ?: 0
                    )
                }
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())
}
