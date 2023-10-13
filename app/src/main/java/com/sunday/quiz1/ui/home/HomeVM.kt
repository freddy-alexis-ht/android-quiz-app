package com.sunday.quiz1.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.question.QuestionState
import com.sunday.quiz1.ui.common.Routes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeVM : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private val _appEvent = Channel<AppEvent>()
    val appEvent = _appEvent.receiveAsFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnStart -> {
                state = state.copy(isNew = true)
                sendAppEvent(
                    AppEvent.Navigate(Routes.QUIZ_QUESTION+ "?newQuiz=" + true)
                )
            }
            HomeEvent.OnContinue -> {
                state = state.copy(isNew = false)
                sendAppEvent(
                    AppEvent.Navigate(Routes.QUIZ_QUESTION+ "?newQuiz=" + false)
                )
            }
            is HomeEvent.OnExit -> {
                event.activity?.finish()
            }
        }
    }

    private fun sendAppEvent(event: AppEvent) {
        viewModelScope.launch {
            _appEvent.send(event)
        }
    }
    fun hideLoading() {
        viewModelScope.launch {
            delay(500L)
            state = state.copy(isNew = false)
        }
    }
}