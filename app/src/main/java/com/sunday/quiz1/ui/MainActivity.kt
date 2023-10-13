package com.sunday.quiz1.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.sunday.quiz1.ui.common.Navigation
import com.sunday.quiz1.ui.home.HomeVM
import com.sunday.quiz1.ui.question.QuestionVM
import com.sunday.quiz1.ui.result.ResultVM
import com.sunday.quiz1.ui.theme.Quiz1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeVM: HomeVM by viewModels()
    private val questionVM: QuestionVM by viewModels()
    private val resultVM: ResultVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Quiz1Theme {
                window?.statusBarColor = MaterialTheme.colors.surface.toArgb()
                Box(modifier = Modifier.fillMaxSize()
                    .background(Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.surface,
                            MaterialTheme.colors.background,
                        ),
                        startY = 800f
                    )),
                ) {
                    Navigation(homeVM = homeVM, questionVM = questionVM, resultVM = resultVM)
                }
            }
        }
    }
}
