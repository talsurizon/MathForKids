package com.mathforkids.ui.screen.topics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mathforkids.domain.model.Grade
import com.mathforkids.domain.model.Topic
import com.mathforkids.ui.components.TopicChip
import com.mathforkids.util.HebrewStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicsScreen(
    grade: Grade,
    windowSizeClass: WindowSizeClass,
    onTopicSelected: (Topic) -> Unit,
    onBack: () -> Unit,
    viewModel: TopicsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val contentPadding = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> 64.dp
        WindowWidthSizeClass.Medium -> 32.dp
        else -> 16.dp
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${grade.hebrewName} - ${HebrewStrings.SELECT_TOPIC}",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = HebrewStrings.BACK_TO_HOME
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = contentPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(uiState.topics) { topicInfo ->
                TopicChip(
                    topic = topicInfo.topic,
                    bestStars = topicInfo.bestStars,
                    onClick = { onTopicSelected(topicInfo.topic) }
                )
            }
        }
    }
}
