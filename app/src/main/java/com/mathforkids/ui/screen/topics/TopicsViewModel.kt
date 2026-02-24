package com.mathforkids.ui.screen.topics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Progress
import com.mathforkids.domain.model.Topic
import com.mathforkids.domain.usecase.GetProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class TopicsUiState(
    val grade: Grade = Grade.GRADE_1,
    val topics: List<TopicInfo> = emptyList()
)

data class TopicInfo(
    val topic: Topic,
    val bestStars: Int = 0
)

@HiltViewModel
class TopicsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getProgressUseCase: GetProgressUseCase
) : ViewModel() {

    private val gradeLevel: Int = savedStateHandle["gradeLevel"] ?: 1
    private val grade = Grade.fromLevel(gradeLevel)

    val uiState: StateFlow<TopicsUiState> = getProgressUseCase.forGrade(grade)
        .map { progressList ->
            TopicsUiState(
                grade = grade,
                topics = Topic.topicsForGrade(grade).map { topic ->
                    val progress = progressList.find { it.topic == topic }
                    TopicInfo(topic = topic, bestStars = progress?.bestStars ?: 0)
                }
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TopicsUiState(grade = grade))
}
