package com.sunday.quiz1.ui.question

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunday.quiz1.ui.common.AppEvent
import com.sunday.quiz1.ui.result.ResultState
import com.sunday.quiz1.ui.common.Routes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class QuestionVM : ViewModel() {

    var state by mutableStateOf(QuestionState())
        private set
    var userOptions = state.userOptions.toMutableStateList()
        private set
    var resultState by mutableStateOf(ResultState())
        private set

    private val _appEvent = Channel<AppEvent>()
    val appEvent = _appEvent.receiveAsFlow()

    fun onEvent(event: QuestionEvent) {
        when (event) {
            is QuestionEvent.OnRbChange -> onRbChange(event.optionSelected, event.index)
            is QuestionEvent.OnPrevious -> onPrevious(event.index)
            is QuestionEvent.OnNext -> onNext(event.index)
            is QuestionEvent.OnFinish -> onFinish(event.index)
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
        generateQuizResults()
        for (i in 0 until Question.getSize()) {
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
        var i: Int
        if (userOptions[index] != "") {
            i = Question.getOne(index).options.indexOf(userOptions[index])
        } else {
            i = Question.getOne(index).options.indexOf(state.optionSelected)
        }

        var b: Boolean? = if (i != -1) Question.getOne(index).results[i]
        else null

        state.userAnswers[index] = b
        if (userOptions[index] == "") {
            userOptions[index] = state.optionSelected
        }
    }

    private fun generateQuizResults() {
        resultState = resultState.copy(
            totalQuestions = Question.getSize(),
            totalCorrect = state.userAnswers.count { it == true },
            totalIncorrect = state.userAnswers.count { it == false },
            totalNotAnswered = state.userAnswers.count { it == null },
            correctQuestions = this.getCorrectQuestions(),
            incorrectQuestions = this.getIncorrectQuestions(),
            notAnsweredQuestions = this.getNotAnsweredQuestions()
        )
    }

    private fun getCorrectQuestions(): String {
        var n: Int = 1
        var correct: String = "*"
        state.userAnswers.forEach {
            if (it == true) correct += " $n"
            n++
        }
        return correct
    }

    private fun getIncorrectQuestions(): String {
        var n: Int = 1
        var incorrect: String = "*"
        state.userAnswers.forEach {
            if (it == false) incorrect += " $n"
            n++
        }
        return incorrect
    }

    private fun getNotAnsweredQuestions(): String {
        var n: Int = 1
        var notAnswered: String = "*"
        state.userAnswers.forEach {
            if (it == null) notAnswered += " $n"
            n++
        }
        return notAnswered
    }

    fun clearUserOptions() {
        for (i in 0 until Question.getSize()) {
            userOptions[i] = ""
        }
        state = state.copy(
            userOptions = userOptions,
            optionSelected = ""
        )
    }
}