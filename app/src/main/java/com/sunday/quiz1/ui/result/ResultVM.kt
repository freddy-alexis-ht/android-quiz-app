package com.sunday.quiz1.ui.result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.common.Routes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ResultVM : ViewModel() {

    var resultState by mutableStateOf(ResultState())
        private set

    private val _appEvent = Channel<AppEvent>()
    val appEvent = _appEvent.receiveAsFlow()

    fun onEvent(event: ResultEvent) {
        when (event) {
            ResultEvent.OnHome -> {
                sendAppEvent(
                    AppEvent.Navigate(Routes.QUIZ_HOME + "?mem=" + true)
                )
            }
            ResultEvent.OnViewHide -> {
                resultState = resultState.copy(
                    isDetailVisible = !resultState.isDetailVisible,
                    isCorrectVisible = !resultState.isDetailVisible,
                    isIncorrectVisible = !resultState.isDetailVisible,
                    isNotAnsweredVisible = !resultState.isDetailVisible,
                )
            }
            is ResultEvent.OnClickFilter -> {
                when (event.answerType) {
                    true -> {
                        resultState = resultState.copy(
                            isCorrectVisible = !resultState.isCorrectVisible,
                        )
                    }
                    false -> {
                        resultState = resultState.copy(
                            isIncorrectVisible = !resultState.isIncorrectVisible,
                        )
                    }
                    else -> {
                        resultState = resultState.copy(
                            isNotAnsweredVisible = !resultState.isNotAnsweredVisible,
                        )
                    }
                }
            }
        }
    }

    private fun sendAppEvent(event: AppEvent) {
        viewModelScope.launch {
            _appEvent.send(event)
        }
    }

    /**/
    fun updateResultState(result: ResultState) {
        resultState = result
    }
}