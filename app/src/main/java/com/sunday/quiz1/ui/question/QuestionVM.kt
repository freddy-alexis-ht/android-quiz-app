package com.sunday.quiz1.ui.question

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunday.quiz1.data.model.Question
import com.sunday.quiz1.data.model.Timer
import com.sunday.quiz1.domain.use_case.GetAllQuestionsUseCase
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.result.ResultState
import com.sunday.quiz1.ui.common.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionVM @Inject constructor(
    private val getAllQuestionsUseCase: GetAllQuestionsUseCase,
) : ViewModel() {

    lateinit var questions: List<Question>
    init {
        viewModelScope.launch {
            questions = getAllQuestionsUseCase().data!!
        }
    }

    var timer by mutableStateOf(Timer())
        private set
    var state by mutableStateOf(QuestionState())
        private set
    var userOptions = state.userOptions.toMutableStateList()
        private set

    private val _appEvent = Channel<AppEvent>()
    val appEvent = _appEvent.receiveAsFlow()

    fun onEvent(event: QuestionEvent) {
        when (event) {
            is QuestionEvent.OnPlayPause -> timer = timer.copy(playPause = event.playPause)
            is QuestionEvent.OnRbChange -> onRbChange(event.optionSelected, event.index)
            is QuestionEvent.OnPrevious -> onPrevious(event.index)
            is QuestionEvent.OnNext -> onNext(event.index)
            is QuestionEvent.OnFinish -> onFinish(event.index)
            is QuestionEvent.OnJumpTo -> state = state.copy(index = event.index)
            QuestionEvent.OnHome -> onHome()
        }
    }

    private fun onRbChange(optionSelected: String, index: Int) {
        state = state.copy(
            optionSelected = optionSelected
        )
        userOptions[index] = optionSelected
    }

    private fun onPrevious(index: Int) {
        state = state.copy(
            index = index.dec(),
        )
    }

    private fun onNext(index: Int) {
        validateUserOption(index)
        state = state.copy(
            optionSelected = "",
            index = index.inc()
        )
    }

    private fun onFinish(index: Int) {
        validateUserOption(index)
        sendResultsToResultScreen()
        for (i in questions.indices) {
            userOptions[i] = ""
        }
        state = state.copy(index = 0)
        sendAppEvent(
            AppEvent.Navigate(Routes.QUIZ_RESULTS)
        )
    }

    private fun onHome() {
        state = state.copy(index = 0)
        sendAppEvent(
            AppEvent.Navigate(Routes.QUIZ_HOME + "?mem=" + false)
        )
    }

    private fun sendAppEvent(event: AppEvent) {
        viewModelScope.launch {
            _appEvent.send(event)
        }
    }

    /* Utilitary functions */
    private fun validateUserOption(index: Int) {
        var userAnswer = if(userOptions[index] != "") userOptions[index]
        else state.optionSelected

        var userBoolean = if(userAnswer == "") null
        else userAnswer == questions[index].result

        state.userAnswers[index] = userBoolean

        if (userOptions[index] == "") {
            userOptions[index] = state.optionSelected
        }
    }

    private fun sendResultsToResultScreen() {
        ResultState.size = questions.size
        ResultState.userOptions = userOptions.toMutableList()
        ResultState.userAnswers = state.userAnswers
    }

    fun clearUserOptions() {
        for (i in questions.indices) {
            userOptions[i] = ""
        }
        state = state.copy(
            userOptions = userOptions,
            optionSelected = ""
        )
    }

    fun increaseTimer(ticks: Int) {
        timer = timer.copy(ticks = ticks)
    }
}