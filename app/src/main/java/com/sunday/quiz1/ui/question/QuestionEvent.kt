package com.sunday.quiz1.ui.question

sealed class QuestionEvent {
    data class OnPlayPause(val playPause: Boolean): QuestionEvent()
    data class OnRbChange(val optionSelected: String, val index: Int) : QuestionEvent()
    data class OnPrevious(val index: Int): QuestionEvent()
    data class OnNext(val index: Int): QuestionEvent()
    data class OnFinish(val index: Int): QuestionEvent()
    data class OnJumpTo(val index: Int): QuestionEvent()
    object OnHome: QuestionEvent()
}
