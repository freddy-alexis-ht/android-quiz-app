package com.sunday.quiz1.ui.result

sealed class ResultEvent {
    object OnHome: ResultEvent()
    object OnViewHide: ResultEvent()
    data class OnClickFilter(val answerType: Boolean?): ResultEvent()
}
