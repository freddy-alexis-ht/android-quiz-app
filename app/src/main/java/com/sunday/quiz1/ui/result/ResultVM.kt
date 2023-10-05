package com.sunday.quiz1.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.common.Routes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ResultVM : ViewModel() {

//    var state by mutableStateOf(ResultState())
//        private set

    private val _appEvent = Channel<AppEvent>()
    val appEvent = _appEvent.receiveAsFlow()

    fun onEvent(event: ResultEvent) {
        when (event) {
            is ResultEvent.OnHome -> {
                sendAppEvent(
                    AppEvent.Navigate(Routes.QUIZ_HOME + "?mem=" + true)
                )
            }
        }
    }

    private fun sendAppEvent(event: AppEvent) {
        viewModelScope.launch {
            _appEvent.send(event)
        }
    }
}