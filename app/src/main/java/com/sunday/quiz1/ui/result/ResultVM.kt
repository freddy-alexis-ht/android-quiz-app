package com.sunday.quiz1.ui.result

import android.util.Log
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
            is ResultEvent.OnHome -> {
                sendAppEvent(
                    AppEvent.Navigate(Routes.QUIZ_HOME + "?mem=" + true)
                )
            }
            is ResultEvent.OnViewHide -> {
                Log.i("MyTag", "ResultVM1: ${event.isDetailVisible}")
                Log.i("MyTag", "ResultVM1: ${resultState.isDetailVisible}")
                resultState = resultState.copy(isDetailVisible = !event.isDetailVisible)
                Log.i("MyTag", "ResultVM2: ${resultState.isDetailVisible}")
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