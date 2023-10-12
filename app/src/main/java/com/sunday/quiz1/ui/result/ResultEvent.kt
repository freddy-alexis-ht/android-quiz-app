package com.sunday.quiz1.ui.result

sealed class ResultEvent {
    object OnHome: ResultEvent()
    data class OnViewHide(val isDetailVisible: Boolean): ResultEvent()
}
