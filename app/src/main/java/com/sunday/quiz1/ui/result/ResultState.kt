package com.sunday.quiz1.ui.result

data class ResultState(
    var totalQuestions: Int = 0,
    var totalCorrect: Int = 0,
    var totalIncorrect: Int = 0,
    var totalNotAnswered: Int = 0,
    var correctQuestions: String = "",
    var incorrectQuestions: String = "",
    var notAnsweredQuestions: String = ""
)
