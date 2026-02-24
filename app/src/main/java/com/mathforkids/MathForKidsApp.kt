package com.mathforkids

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mathforkids.ui.navigation.NavGraph
import com.mathforkids.ui.theme.MathForKidsTheme

@Composable
fun MathForKidsApp(windowSizeClass: WindowSizeClass) {
    MathForKidsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navController = rememberNavController()
            NavGraph(
                navController = navController,
                windowSizeClass = windowSizeClass
            )
        }
    }
}
